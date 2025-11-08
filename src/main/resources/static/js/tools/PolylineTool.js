import Tool from "./Tool.js";

export default class PolylineTool extends Tool{
    static points = [];

    static get SVG_TYPE() {
        return "polyline";
    }

    static get SHAPE_TYPE() {
        return "Polyline";
    }

    static create() {
        this.shape = this.createSvgElem();
        this.setSvgParams();
        this.svg.appendChild(this.shape);
    }

    static draw(){
        if (this.points.length === 4)
            this.create();
        if (this.shape)
            this.shape.setAttributeNS(null, 'points', this.points);
    }

    static svgToJSON(svgElement) {
        const attributesObject = super.svgToJSON(svgElement);
        const cord = svgElement.attributes.getNamedItem("points").value.split(',');
        let points = [];

        for (let i = 0, j = 0; i < cord.length; i += 2, j++) {
            points.push({
                "x" : parseFloat(cord[i]),
                "y" : parseFloat(cord[i + 1])
            });
        }

        attributesObject["points"] = points;
        return attributesObject;
    }

    static handleKeyPress = (event) => {
        if (event.key === 'Enter') {
            this.points = [];
            document.removeEventListener('keydown', this.handleKeyPress);
            this.sendSvgToServer('/add', this.svgToJSON(this.shape));
        }
    }

    static mousedownEvent = (event) => {
        if (event.shiftKey)
            return;

        document.addEventListener('keydown', this.handleKeyPress);

        const dot = this.svgPoint(event.clientX, event.clientY);
        this.points.push(...[dot.x, dot.y]);

        this.draw();
    }

    static moveShape(dx, dy) {
        let cord = this.shape.getAttribute('points').split(',');
        let points = [];

        for (let i = 0; i < cord.length; i += 2) {
            points.push(parseFloat(cord[i]) + dx);
            points.push(parseFloat(cord[i + 1]) + dy);
        }

        this.shape.setAttributeNS(null, 'points', points);
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
        this.shape = null;
    }
}