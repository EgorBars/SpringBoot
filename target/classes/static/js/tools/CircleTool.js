import Tool from "./Tool.js";

export default class CircleTool extends Tool {
    static get SVG_TYPE() {
        return "circle";
    }

    static get SHAPE_TYPE() {
        return "Circle";
    }

    static svgToJSON(svgElement) {
        const attributesObject = super.svgToJSON(svgElement);
        attributesObject["points"] = [
            {
                "x" : svgElement.getAttribute("cx"),
                "y" : svgElement.getAttribute("cy")
            },
            {
                "x" : svgElement.getAttribute("r"),
                "y" : svgElement.getAttribute("r")
            }
        ]
        return attributesObject;
    }

    static draw(start, end){
        // Отрисовка от центра
        // this.shape.setAttributeNS(null, 'cx', start.x);
        // this.shape.setAttributeNS(null, 'cy', start.y);
        // this.shape.setAttributeNS(null, 'r', Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2)));

        const d = Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2)) / 1.414;
        let cx = start.x < end.x ? start.x + d / 2 : start.x - d / 2;
        let cy = start.y < end.y ? start.y + d / 2 : start.y - d / 2;

        this.shape.setAttributeNS(null, 'cx', cx);
        this.shape.setAttributeNS(null, 'cy', cy);
        this.shape.setAttributeNS(null, 'r', d / 2);
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
        let x = parseFloat(this.shape.getAttributeNS(null, 'cx')) + dx;
        let y = parseFloat(this.shape.getAttributeNS(null, 'cy')) + dy;
        this.shape.setAttributeNS(null, 'cx', x);
        this.shape.setAttributeNS(null, 'cy', y);
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