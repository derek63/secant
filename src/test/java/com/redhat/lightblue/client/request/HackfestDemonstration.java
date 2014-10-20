package com.redhat.lightblue.client.request;

import com.redhat.lightblue.client.LightblueClient;
import com.redhat.lightblue.client.enums.NaryOperation;
import com.redhat.lightblue.client.expression.ArrayExpression;
import com.redhat.lightblue.client.expression.Expression;
import com.redhat.lightblue.client.expression.NaryLogicalExpression;
import com.redhat.lightblue.client.expression.ValueExpression;
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

        List<Expression> conditions = new ArrayList<>();

        conditions.add(new ValueExpression("termsTypeCode = NDA"));
        conditions.add(new ValueExpression("statusCode = active"));

        conditions.add(
           new ArrayExpression("termsVerbiage",
             new NaryLogicalExpression(
                                NaryOperation.AND,  // the next two lines are the array sig
                                new ValueExpression("statusCode = active"),
                                new ArrayExpression("termsVerbiageTranslation", new ValueExpression("statusCode = active")))));
        request.where(new NaryLogicalExpression(NaryOperation.AND, conditions));

        request.select(
                new FieldProjection("_id", true, false),
                new ArrayProjection("termsVerbiage", true, new ValueExpression("statusCode = active"),
                        new ArrayProjection("termsVerbiageTranslation", true, new ValueExpression("statusCode = inactive"),
                                new FieldProjection("*", true, true)
                        )
                )
        );
        System.out.println(client.data(request));
    }
}
