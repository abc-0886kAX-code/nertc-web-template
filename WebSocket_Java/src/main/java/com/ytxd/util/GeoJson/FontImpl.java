package com.ytxd.util.GeoJson;

import org.geotools.filter.IllegalFilterException;
import org.geotools.styling.Font;
import org.opengis.filter.expression.Expression;
import org.opengis.style.StyleVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TY
 */
public class FontImpl implements Font {
    private final List<Expression> fontFamily = new ArrayList();
    private Expression fontSize = null;
    private Expression fontStyle = null;
    private Expression fontWeight = null;
    public FontImpl() {

    }
    public FontImpl(Expression fontSize, Expression fontStyle, Expression fontWeight){
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.fontWeight = fontWeight;
    }
    @Override
    public List<Expression> getFamily() {
        return this.fontFamily;
    }
    @Override
    public Expression getSize() {
        return this.fontSize;
    }

    @Override
    public Object accept(StyleVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }


    @Override
    public void setSize(Expression size) {
        this.fontSize = size;
    }
    @Override
    public Expression getStyle() {
        return this.fontStyle;
    }

    @Override
    public void setStyle(Expression style) {
        this.fontStyle = style;
    }
    @Override
    public Expression getWeight() {
        return this.fontWeight;
    }
    @Override
    public void setWeight(Expression weight) {
        this.fontWeight = weight;
    }

    public Font getFont() {
        FontImpl font = new FontImpl();
        try {
            font.setSize(this.fontSize);
            font.setStyle(this.fontStyle);
            font.setWeight(this.fontWeight);
            return font;
        } catch (IllegalFilterException var3) {
            throw new RuntimeException("Error creating default", var3);
        }
    }
}
