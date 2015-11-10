package lv.lpb.rest;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lv.lpb.domain.Currency;
import lv.lpb.domain.Merchant;

@Path("/json/merchants")
public class JSONService {

//    @GET
//    @Path("/get")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Track getTrackInJSON() {
//
//        Track track = new Track();
//        track.setTitle("Enter Sandman");
//        track.setSinger("Metallica");
//
//        return track;
//
//    }
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchantsList() {
        List<Merchant> merchantList = new ArrayList<>();

        GenericEntity<List<Merchant>> list = new GenericEntity<List<Merchant>>(merchantList){};
        Merchant merchant = new Merchant();
        Merchant merchant2 = new Merchant();
        merchant2.add(Currency.RUB);

        merchantList.add(merchant);
        merchantList.add(merchant2);
        
//        System.out.println("z-z-z-z-z");
//        System.out.println(merchant);
//        System.out.println(merchant2);
        return Response.ok(list).build();
    }

//    @POST
//    @Path("/post")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response createTrackInJSON(Track track) {
//
//        String result = "Track saved : " + track;
//        return Response.status(201).entity(result).build();
//
//    }

}
