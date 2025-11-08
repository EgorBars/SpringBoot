export default class Tool {
    static shape;
    static fill = "red";
    static stroke = "black";
    static strokeWidth = 6;

    static get SVG_TYPE() {
        return "";
    }

    static get SHAPE_TYPE() {
        return "";
    }

    static createSvgElem() {
        return document.createElementNS('http://www.w3.org/2000/svg', this.SVG_TYPE);
    }

    static setSvgParams() {
        this.shape.setAttributeNS(null, 'fill', this.fill);
        this.shape.setAttributeNS(null, 'stroke', this.stroke);
        this.shape.setAttributeNS(null, 'stroke-width', this.strokeWidth);
        this.shape.setAttributeNS(null, 'data-id', this.getNewId());
        this.shape.setAttributeNS(null, 'data-type', this.SHAPE_TYPE);
    }

    static init(svg) {
        this.svg = svg;
        return this;
    }

    static select() {
        this.svg.addEventListener('mousedown', this.mousedownEvent);
    }

    static setParams(fill, stroke, strokeWidth) {
        this.fill = fill;
        this.stroke = stroke;
        this.strokeWidth = strokeWidth;
    }

    static getNewId() {
        if (svg.lastElementChild !== null)
            return parseInt(svg.lastElementChild.getAttribute('data-id')) + 1;
        else
            return 0;
    }

    static svgToJSON(svgElement) {
        const attributes = svgElement.attributes;
        const attributesObject = {};

        attributesObject["type"] = attributes.getNamedItem("data-type").value;
        attributesObject["id"] = attributes.getNamedItem("data-id").value;
        attributesObject["fill"] = attributes.getNamedItem("fill").value;
        attributesObject["stroke"] = attributes.getNamedItem("stroke").value;
        attributesObject["strokeWidth"] = attributes.getNamedItem("stroke-width").value;

        return attributesObject;
    }

    static sendSvgToServer(path, data){
        fetch(path, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        }).then((response) => {
            if (!response.ok)
                console.error('Error sending SVG data to server:', response.status);
            return response;
        }).then((result) => {
            console.log('Server response:', result.json());
            if (path !== "/change")
                window.location = window.location.href;
        }).catch((error) => {
            console.error('An error occurred:', error);
        })
    };

    static svgPoint(x, y){
        let p = this.svg.createSVGPoint();
        p.x = x;
        p.y = y;
        return p.matrixTransform(this.svg.getScreenCTM().inverse());
    }

    static create(start, end){
        this.shape = this.createSvgElem();
        this.setSvgParams();
        this.svg.appendChild(this.shape);
    }
}