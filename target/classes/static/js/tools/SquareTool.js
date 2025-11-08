import Tool from "./Tool.js";

export default class SquareTool extends Tool {
    static get SVG_TYPE() {
        return "rect";
    }

    static get SHAPE_TYPE() {
        return "Square";
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
        const side = Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2)) / 1.414;
        const x = start.x < end.x ? start.x : start.x - side;
        const y = start.y < end.y ? start.y : start.y - side;

        this.shape.setAttributeNS(null, 'x', x);
        this.shape.setAttributeNS(null, 'y', y);
        this.shape.setAttributeNS(null, 'width', side);
        this.shape.setAttributeNS(null, 'height', side);
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
        this.wasMoved = false;
        this.shape = shape;
        this.start = this.svgPoint(event.clientX, event.clientY);

        this.svg.addEventListener('mousemove', this.drag);
        this.svg.addEventListener('mouseup', this.endDrag);
    }

    static drag = (event) => {
        let endCoords = this.svgPoint(event.clientX, event.clientY);
        let dx = endCoords.x - this.start.x;
        let dy = endCoords.y - this.start.y;
        if (dx + dy !== 0) {
            this.wasMoved = true;
            this.moveShape(dx, dy);
            this.start = endCoords;
        }
    }

    static endDrag = () => {
        this.svg.removeEventListener('mousemove', this.drag);
        this.svg.removeEventListener('mouseup',  this.endDrag);
        if (this.wasMoved)
            this.sendSvgToServer('/change', this.svgToJSON(this.shape));
    }
}