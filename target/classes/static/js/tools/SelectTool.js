import Tool from "./Tool.js";

export default class SelectTool extends Tool {
    static shapeTool;
    static restoreInputsState;

    static createBorder() {
        this.shape.setAttribute("class", "selected");
        this.shape.setAttributeNS(null, "stroke-dasharray", "10,5");
    }

    static deleteBorder() {
        this.shape.removeAttribute("class");
        this.shape.removeAttributeNS(null, "stroke-dasharray");
    }

    static selectShape(shape, tool, setInputsState, restoreInputsState) {
        if (shape === this.shape) {
            this.unselectShape();
            return;
        }
        if (this.shape)
            this.unselectShape();
        setInputsState(shape.getAttributeNS(null, "fill"), shape.getAttributeNS(null, "stroke"), shape.getAttributeNS(null, "stroke-width"));
        this.restoreInputsState = restoreInputsState;
        this.shape = shape;
        this.shapeTool = tool;
        this.wasModifed = false;
        this.createBorder();
        document.addEventListener('keydown', this.handleKeyPress);
    }

    static unselectShape() {
        this.restoreInputsState();
        this.deleteBorder();
        if (this.wasModifed)
            this.sendSvgToServer('/change', this.shapeTool.svgToJSON(this.shape));
        this.shape = null;
        this.shapeTool = null;
        document.removeEventListener('keydown', this.handleKeyPress);
    }

    static deleteShape() {
        this.restoreInputsState();
        this.shape.remove();
        this.sendSvgToServer('/delete', this.shape.getAttributeNS(null, "data-id"));
        this.shape = null;
        this.shapeTool = null;
        document.removeEventListener('keydown', this.handleKeyPress);
    }

    static setParams(fill, stroke, strokeWidth) {
        if (this.shape) {
            this.wasModifed = true;
            this.shape.setAttributeNS(null, 'fill', fill);
            this.shape.setAttributeNS(null, 'stroke', stroke);
            this.shape.setAttributeNS(null, 'stroke-width', strokeWidth);
        }
    }

    static handleKeyPress = (event) => {
        if (event.key === 'Enter') {
            this.unselectShape();
            return;
        }
        if (event.key === 'Delete')
            this.deleteShape();
    }

    static mousedownEvent = () => {
    }
}