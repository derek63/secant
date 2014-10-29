package com.redhat.lightblue.client.request;

import com.redhat.lightblue.client.LightblueClient;
import com.redhat.lightblue.client.enums.NaryOperation;
import com.redhat.lightblue.client.expression.query.*;
import com.redhat.lightblue.client.expression.query.NaryLogicalQuery;
import com.redhat.lightblue.client.expression.query.Query;
import com.redhat.lightblue.client.projection.ArrayProjection;
import com.redhat.lightblue.client.projection.FieldProjection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jblashka on 10/10/14.
 */
public class HackfestDemonstration {

    public static void main(String[] args) {
        LightblueClient client = new LightblueClient();
        DataFindRequest request = new DataFindRequest("terms","0.14.0-SNAPSHOT");

        List<Query> conditions = new ArrayList<>();

        conditions.add(new ValueQuery("termsTypeCode = NDA"));
        conditions.add(new ValueQuery("statusCode = active"));

        conditions.add(
           new ArrayQuery("termsVerbiage",
             new NaryLogicalQuery(
                                NaryOperation.AND,  // the next two lines are the array sig
                                new ValueQuery("statusCode = active"),
                                new ArrayQuery("termsVerbiageTranslation", new ValueQuery("statusCode = active")))));
        request.where(new NaryLogicalQuery(NaryOperation.AND, conditions));

        request.select(
                new FieldProjection("_id", true, false),
                new ArrayProjection("termsVerbiage", true, new ValueQuery("statusCode = active"),
                        new ArrayProjection("termsVerbiageTranslation", true, new ValueQuery("statusCode = inactive"),
                                new FieldProjection("*", true, true)
                        )
                )
        );
        System.out.println(client.data(request));
    }
}
