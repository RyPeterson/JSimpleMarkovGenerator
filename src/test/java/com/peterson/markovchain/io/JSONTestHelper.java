package com.peterson.markovchain.io;

/**
 * Created by Ryan Peterson on 01/10/2016
 */
public class JSONTestHelper
{
    public static final String BASIC_CHAIN = "{\"@\":[\"World.\",\"seashore.\"],\"the\":[\"seashore.\"],\"She\":[\"sell\"],\"Test.\":[],\"$\":[\"Test.\",\"Hello\",\"She\"],\"Hello\":[\"World.\"],\"seashells\":[\"by\"],\"sell\":[\"seashells\"],\"by\":[\"the\"]}";

    public static final String TRAVERSAL_CHAIN = "{\"@\":[{\"word\":\"World.\"},{\"word\":\"seashore.\"}],\"the\":[{\"word\":\"seashore.\"}],\"She\":[{\"previous\":{\"word\":\"She\"},\"word\":\"sell\"}],\"Test.\":[],\"$\":[{\"word\":\"Test.\"},{\"word\":\"Hello\"},{\"word\":\"She\"}],\"Hello\":[{\"previous\":{\"word\":\"Hello\"},\"word\":\"World.\"}],\"seashells\":[{\"word\":\"by\"}],\"sell\":[{\"word\":\"seashells\"}],\"by\":[{\"word\":\"the\"}]}";
}
