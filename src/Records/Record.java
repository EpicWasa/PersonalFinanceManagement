package Records;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "day", propOrder = {"date", "sum", "comment"})
public class Record implements Comparable<Record>{
    public Record(){};

    public Record( int v, String s)
    {
        this.date = " ";
        this.sum = v;
        this.comment = s;
    }

    public Record(String d, int v, String s)
    {
        this.date = d;
        this.sum = v;
        this.comment = s;
    }




    public void setComment(String comment) {
        this.comment = comment;
    }


    public void setSum(int sum) {
        this.sum = sum;
    }

   @XmlAttribute(name = "sum")
    public int getSum() {
        return sum;
    }

   @XmlAttribute( name = "comment")
    public String getComment() {
        return comment;
    }


    private Integer sum;

    @XmlAttribute(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

    private String comment;

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj==null || !(obj instanceof Record)){
            return false;
        }
        Record record = (Record)obj;
        return getSum()== record.getSum() && getComment().equals(record.getComment());
    }

    @Override
    public String toString()
    {
        return this.getDate()+ Integer.toString(this.getSum()) + ' ' + this.getComment();
    }

    @Override
    public int compareTo(Record o) {
        return Integer.compare(this.getSum(), o.getSum());
    }



}
