package com.example.serialization.json;

import org.junit.Test;

//import java.awt.List;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;


import static junit.framework.TestCase.assertEquals;

public class JsonParserTest {
    private static final String SERIALIZED = "{\"menu\":{\"id\":1,\"value\":\"File\"," +
            "\"popup\":{\"menuitem\":[{\"value\":\"New\",\"onclick\":\"CreateNewDoc()\"},{\"value\":\"Open\",\"onclick\":\"OpenDoc()\"},{\"value\":\"Close\",\"onclick\":\"CloseDoc()\"}]}}}";

    private static final Map<String, Object> DESERIALIZED = getJavaRepresentation();

    private JsonSerdes jsonParser = new JsonSerdes();

    @Test
    public void givenJsonStringWhenDeserializeThenReturnCorrectMap() throws Exception {
        Map<String, Object> actual = jsonParser.deserialize(SERIALIZED);
        assertEquals(DESERIALIZED, actual);
    }

    @Test
    public void givenMapWhenSerializeThenReturnCorrectJsonString() throws Exception {
        String actual = jsonParser.serialize(DESERIALIZED);
        assertEquals(SERIALIZED, actual);
    }

    @Mock JsonSerdes jsonSer;

    @Test
    public void mockitoTestUsage(){

        List<String> fakeList = Mockito.mock(List.class);


        Mockito.when(fakeList.get(ArgumentMatchers.anyInt())).thenReturn("next");

        for(Integer i = 0; i < 5; i++){
            String myResult = fakeList.get(i);
        }


        //Mockito.verify()
    }


    private static Map<String, Object> getJavaRepresentation() {
        Map<String, Object> menuitem1 = new LinkedHashMap<>();
        Map<String, Object> menuitem2 = new LinkedHashMap<>();
        Map<String, Object> menuitem3 = new LinkedHashMap<>();

        menuitem1.put("value", "New");
        menuitem1.put("onclick", "CreateNewDoc()");

        menuitem2.put("value", "Open");
        menuitem2.put("onclick", "OpenDoc()");

        menuitem3.put("value", "Close");
        menuitem3.put("onclick", "CloseDoc()");

        Map<String, Object> popup = new LinkedHashMap<>();
        popup.put("menuitem", Arrays.asList(menuitem1, menuitem2, menuitem3));

        Map<String, Object> menu = new LinkedHashMap<>();
        menu.put("id", 1);
        menu.put("value", "File");
        menu.put("popup", popup);

        Map<String, Object> ret = new LinkedHashMap<>();
        ret.put("menu", menu);

        return ret;
    }
}
