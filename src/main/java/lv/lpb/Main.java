package lv.lpb;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import lv.lpb.domain.Currency;
import lv.lpb.domain.Exporter;
import lv.lpb.domain.Transaction;

public class Main {

    public static void main(String[] args) throws JAXBException, IOException {

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setMerchantId(1L);
        transaction.setAmount(new BigDecimal(10));
        transaction.setCurrency(Currency.EUR);
        transaction.setStatus(Transaction.Status.INIT);
        transaction.setInitDate(LocalDate.of(2015, Month.NOVEMBER, 1));
////        
////        File file = new File("C:\\Programming\\file.xml");
////        JAXBContext jaxbContext = JAXBContext.newInstance(Transaction.class);
////        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
////        
////        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
////        jaxbMarshaller.marshal(transaction, file);
////	jaxbMarshaller.marshal(transaction, System.out);
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        mapper.writeValue(new File("C:\\Programming\\transaction.json"), transaction);
//
//        //Convert object to JSON string
//        String jsonInString = mapper.writeValueAsString(transaction);
//        System.out.println(jsonInString);
//
//        //Convert object to JSON string and pretty print
//        jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transaction);
//        System.out.println(jsonInString);
        
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        transactionList.add(transaction);
        Exporter exporter = new Exporter(transactionList);
        System.out.println(exporter);
        
    }
}
