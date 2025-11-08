import Tool from "./Tool.js";

export default class LineTool extends Tool{
    static get SVG_TYPE() {
        return "line";
    }

    static get SHAPE_TYPE() {
        return "Line";
    }

    static svgToJSON(svgElement) {
        const attributesObject = super.svgToJSON(svgElement);
        attributesObject["points"] = [
            {
                "x" : svgElement.getAttribute("x1"),
                "y" : svgElement.getAttribute("y1")
            },
            {
                "x" : svgElement.getAttribute("x2"),
                "y" : svgElement.getAttribute("y2")
            }
        ]
        return attributesObject;
    }

    static draw(start, end){
        this.shape.setAttributeNS(null, 'x1', start.x);
        this.shape.setAttributeNS(null, 'y1', start.y);
        this.shape.setAttributeNS(null, 'x2', end.x);
        this.shape.setAttributeNS(null, 'y2', end.y);
    }

    static mousedownEvent = (event) => {
        if (event.shiftKey)
            return;

        this.svg.addEventListener('mousemove', this.mousemoveEvent);
        this.svg.addEventListener('mouseup', this.mouseupEvent);

        this.start = this.svgPoint(event.clientX, event.clientY);
        this.create(this.start, this.start);
    }

    static mousemoveEvent = (event) => {
        this.end = this.svgPoint(event.clientX, event.clientY);
        this.draw(this.start, this.end);
    }

    static mouseupEvent = () => {
        this.svg.removeEventListener('mousemove', this.mousemoveEvent);
        this.svg.removeEventListener('mouseup', this.mouseupEvent);
        this.sendSvgToServer('/add', this.svgToJSON(this.shape));
    }

    static moveShape(dx, dy){
        let x1 = parseFloat(this.shape.getAttributeNS(null, 'x1')) + dx;
        let y1 = parseFloat(this.shape.getAttributeNS(null, 'y1')) + dy;
        let x2 = parseFloat(this.shape.getAttributeNS(null, 'x2')) + dx;
        let y2 = parseFloat(this.shape.getAttributeNS(null, 'y2')) + dy;
        this.shape.setAttributeNS(null, 'x1', x1);
        this.shape.setAttributeNS(null, 'y1', y1);
        this.shape.setAttributeNS(null, 'x2', x2);
        this.shape.setAttributeNS(null, 'y2', y2);
    }

    static startDrag = (event, shape) => {
        this.shape = shape;
        this.start = this.svgPoint(event.clientX, event.clientY);

        this.svg.addEventListener('mousemove', this.drag);
        this.svg.addEventListener('mouseup', this.endDrag);
    }

    static drag = (event) => {
        let endCoords = this.svgPoint(event.clientX, event.clientY);
        let dx = endCoords.x - this.start.x;
        let dy = endCoords.y - this.start.y;
        this.moveShape(dx, dy);
        this.start = endCoords;
    }

    static endDrag = () => {
        this.svg.removeEventListener('mousemove', this.drag);
        this.svg.removeEventListener('mouseup',  this.endDrag);
        this.sendSvgToServer('/change', this.svgToJSON(this.shape));
    }
}