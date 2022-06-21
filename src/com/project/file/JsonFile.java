package com.project.file;

import com.project.board.Board;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class JsonFile {
    public JsonFile() {}

    //json Data JsonFile to write
    public void jsonWriter(String jsonData) {

        try(FileWriter fileWriter = new FileWriter("./boardData.txt")) {
            fileWriter.write(jsonData);
            fileWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //json DeleteData JsonFile to write
    public void deleteJsonWriter(String jsonData) {

        try(FileWriter fileWriter = new FileWriter("./boardDeleteData.txt")) {
            fileWriter.write(jsonData);
            fileWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //json Data to Read
    public LinkedHashMap<Integer, Board> jsonReader() {
        LinkedHashMap<Integer, Board> listedHashMap = new LinkedHashMap<>();

        try(FileReader fileReader = new FileReader("./boardData.txt")) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);

            Iterator itr = jsonObject.keySet().iterator();

            int cnt = 1;

            while (itr.hasNext()) {
                HashMap<String, String> tmpJson = (HashMap<String, String>) jsonObject.get(itr.next());

                listedHashMap.put(cnt, new Board(tmpJson.get("title"), tmpJson.get("content"), tmpJson.get("name"), tmpJson.get("createdTs"), tmpJson.get("updatedTs"), tmpJson.get("deletedTs")));
                cnt++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return listedHashMap;
    }


    //json DeleteData to Read
    public LinkedHashMap<Integer, Board> deleteJsonReader() {
        LinkedHashMap<Integer, Board> listedHashMap = new LinkedHashMap<>();

        try(FileReader fileReader = new FileReader("./boardDeleteData.txt")) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);

            Iterator itr = jsonObject.keySet().iterator();

            int cnt = 1;

            while (itr.hasNext()) {
                HashMap<String, String> tmpJson = (HashMap<String, String>) jsonObject.get(itr.next());

                listedHashMap.put(cnt, new Board(tmpJson.get("title"), tmpJson.get("content"), tmpJson.get("name"), tmpJson.get("createdTs"), tmpJson.get("updatedTs"), tmpJson.get("deletedTs")));
                cnt++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return listedHashMap;
    }
}
