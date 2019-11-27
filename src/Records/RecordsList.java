package Records;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@XmlType(name = "Exhibition")
@XmlRootElement
public class RecordsList {
    public RecordsList() {
        records = new ArrayList<Record>();
    }




    public boolean addRecord(Record record){
        if(records.contains(record)){
            return false;
        }
        this.records.add(record);
        return true;
    }

    public List<Record> daysWithDate(String str){
        List<Record> res = new ArrayList<Record>();
        for(Record record : this.records){
            if(record.getDate().contains(str)){
                res.add(record);
            }
        }
        return res;
    }




    public void commentSort() {
        Collections.sort(records, (x, y)->x.getComment().compareTo(y.getComment()));
    }

   /* public void read() {
        try (FileReader file = new FileReader(inputFile)) {
            JAXBContext context = JAXBContext.newInstance(RecordsList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            RecordsList exh = (RecordsList) unmarshaller.unmarshal(file);
            this.records = exh.records;
            this.surname = exh.surname;
            this.title = exh.title;
        } catch (IOException | JAXBException exc) {
            exc.printStackTrace();
        }
    }*/


   /* public void write() {

        try (FileWriter file = new FileWriter(outputFile)) {
            JAXBContext context = JAXBContext.newInstance(RecordsList.class, Record.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(this, file);
        } catch (IOException | JAXBException exc) {
            exc.printStackTrace();
        }
    }*/

    public void read(String fileName) {
        try (FileReader file = new FileReader(fileName)) {
            JAXBContext context = JAXBContext.newInstance(RecordsList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            RecordsList exh = (RecordsList) unmarshaller.unmarshal(file);
            this.records = exh.records;
        } catch (IOException | JAXBException exc) {
            exc.printStackTrace();
        }
    }


    public void write(String fileName) {

        try (FileWriter file = new FileWriter(fileName)) {
            JAXBContext context = JAXBContext.newInstance(RecordsList.class, Record.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(this, file);
        } catch (IOException | JAXBException exc) {
            exc.printStackTrace();
        }
    }



    public void countSort() {
        Collections.sort(records);
    }


    @XmlElementWrapper(name = "records", nillable = true)
    private ArrayList<Record> records;



    public ArrayList<Record> getRecords() {
        return records;
    }


}

