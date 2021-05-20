package com.xivs.common.parameters.numericalParameters;

public class IntegerParameter extends NumericalParameter<Integer> {
    public IntegerParameter(Integer value) {
        super(value);
    }

    int compareValues(Integer value1, Integer value2) {
        return Integer.compare(value1, value2);
    }

    public IntegerParameter setLowerBound(Integer lowerBound) {
        super.setLowerBound(lowerBound);
        return this;
    }

    public IntegerParameter setUpperBound(Integer upperBound) {
        super.setUpperBound(upperBound);
        return this;
    }

    public boolean parse(String s) {
        try {
            Integer value = Integer.valueOf(s);
            if (!validate(value)) return false;
            return this.set(value);

        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
