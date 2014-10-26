package com.redhat.lightblue.client.request;

import com.redhat.lightblue.client.LightblueClient;
import com.redhat.lightblue.client.enums.NaryOperation;
import com.redhat.lightblue.client.query.*;
import com.redhat.lightblue.client.query.NaryLogicalQueryExpression;
import com.redhat.lightblue.client.query.QueryExpression;
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

        List<QueryExpression> conditions = new ArrayList<>();

        conditions.add(new ValueQueryExpression("termsTypeCode = NDA"));
        conditions.add(new ValueQueryExpression("statusCode = active"));

        conditions.add(
           new ArrayQueryExpression("termsVerbiage",
             new NaryLogicalQueryExpression(
                                NaryOperation.AND,  // the next two lines are the array sig
                                new ValueQueryExpression("statusCode = active"),
                                new ArrayQueryExpression("termsVerbiageTranslation", new ValueQueryExpression("statusCode = active")))));
        request.where(new NaryLogicalQueryExpression(NaryOperation.AND, conditions));

        request.select(
                new FieldProjection("_id", true, false),
                new ArrayProjection("termsVerbiage", true, new ValueQueryExpression("statusCode = active"),
                        new ArrayProjection("termsVerbiageTranslation", true, new ValueQueryExpression("statusCode = inactive"),
                                new FieldProjection("*", true, true)
                        )
                )
        );
        System.out.println(client.data(request));
    }
}
