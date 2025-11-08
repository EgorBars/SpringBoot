import Tool from "./Tool.js";

export default class RectangleTool extends Tool{
    static get SVG_TYPE() {
        return "rect";
    }

    static get SHAPE_TYPE() {
        return "Rectangle";
    }

    static svgToJSON(svgElement) {
        const attributesObject = super.svgToJSON(svgElement);
        attributesObject["points"] = [
            {
                "x" : svgElement.getAttribute("x"),
                "y" : svgElement.getAttribute("y")
            },
            {
                "x" : svgElement.getAttribute("width"),
                "y" : svgElement.getAttribute("height")
            }
        ]
        return attributesObject;
    }

    static draw(start, end){
        this.shape.setAttributeNS(null, 'x', Math.min(start.x, end.x));
        this.shape.setAttributeNS(null, 'y', Math.min(start.y, end.y));
        this.shape.setAttributeNS(null, 'width', Math.abs(start.x - end.x));
        this.shape.setAttributeNS(null, 'height', Math.abs(start.y - end.y));
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
        let x = parseFloat(this.shape.getAttributeNS(null, 'x')) + dx;
        let y = parseFloat(this.shape.getAttributeNS(null, 'y')) + dy;
        this.shape.setAttributeNS(null, 'x', x);
        this.shape.setAttributeNS(null, 'y', y);
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