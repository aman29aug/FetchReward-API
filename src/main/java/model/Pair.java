package model;

public class Pair<I extends Number, I1 extends Number> {

    Integer oldValue;
    Integer currentValue;

    public Pair(Integer oldValue, Integer currentValue) {
        this.oldValue = oldValue;
        this.currentValue = currentValue;
    }

    public Integer getOldValue() {
        return oldValue;
    }

    public void setOldValue(Integer oldValue) {
        this.oldValue = oldValue;
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }
}
