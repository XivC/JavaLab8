package com.xivs.common.parameters.numericalParameters;

public class FloatParameter extends NumericalParameter<Float> {
    public FloatParameter(Float value) {
        super(value);
    }

    int compareValues(Float value1, Float value2) {
        return Float.compare(value1, value2);
    }

    public FloatParameter setLowerBound(Float lowerBound) {
        super.setLowerBound(lowerBound);
        return this;
    }

    public FloatParameter setUpperBound(Float upperBound) {
        super.setUpperBound(upperBound);
        return this;
    }

    public boolean parse(String s) {
        try {
            Float value = Float.valueOf(s);

            if (!validate(value)) return false;
            this.set(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
