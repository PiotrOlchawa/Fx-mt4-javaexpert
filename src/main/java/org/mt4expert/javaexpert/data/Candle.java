package org.mt4expert.javaexpert.data;

import org.mt4expert.javaexpert.config.ExpertConfigurator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Candle {


    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Date date;
    private Integer index = null;
    private String symbol;
    private int period;
    private BreakoutType breakoutType;

    SimpleDateFormat sdf = new SimpleDateFormat(ExpertConfigurator.DATE_FORMAT);


    public Candle(Double open, Double high, Double low, Double close) {
        this.high = high;
        this.low = low;
        this.open = open;
        this.close = close;
    }

    public Candle() {

    }

    public String getPeriodInReadableFormat() {

        switch (this.period) {
            case 1:
                return " 1M ";
            case 5:
                return " 5M ";
            case 15:
                return " 15M ";
            case 30:
                return " 30M ";
            case 60:
                return " 1H ";
            case 240:
                return "4H ";
            case 1440:
                return "1D ";
            case 10080:
                return "1W ";
            case 43200:
                return " 1M ";
            default:
                return " unknown ";
        }
    }

    public BreakoutType getBreakoutType() {
        return breakoutType;
    }

    public void setBreakoutType(BreakoutType breakoutType) {
    this.breakoutType=breakoutType;
    }


    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getDate() {
        return date;
    }

    public String getDateInReadableFormat() {
        return sdf.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Candle candle = (Candle) o;

        if (period != candle.period) return false;
        if (open != null ? !open.equals(candle.open) : candle.open != null) return false;
        if (high != null ? !high.equals(candle.high) : candle.high != null) return false;
        if (low != null ? !low.equals(candle.low) : candle.low != null) return false;
        if (close != null ? !close.equals(candle.close) : candle.close != null) return false;
        if (date != null ? !date.equals(candle.date) : candle.date != null) return false;
        if (index != null ? !index.equals(candle.index) : candle.index != null) return false;
        return symbol != null ? symbol.equals(candle.symbol) : candle.symbol == null;
    }

    @Override
    public int hashCode() {
        int result = open != null ? open.hashCode() : 0;
        result = 31 * result + (high != null ? high.hashCode() : 0);
        result = 31 * result + (low != null ? low.hashCode() : 0);
        result = 31 * result + (close != null ? close.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (index != null ? index.hashCode() : 0);
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        result = 31 * result + period;
        return result;
    }

    @Override
    public String toString() {
        return "Candle{" +
                "open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", date=" + date +
                '}';
    }
}
