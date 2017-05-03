package server;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;


public class Dictionary {
    private static ConcurrentHashMap<String,HashSet<String>> dictionary = new ConcurrentHashMap<>();

/*    public static void readDictionaryFromFile(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("dictionary.dic"));
            String string;
            while((string = br.readLine())!=null){
                String[] words = string.split(" ");
                HashSet<String> hs = new HashSet<>();
                for (int i = 1; i < words.length; i++) {
                    hs.add(words[i]);
                }
                dictionary.put(words[0],hs);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public static boolean deleteWord(ArrayList<String> words){
        String key = words.get(0);
        words.remove(0);
        if(!dictionary.containsKey(key))
            return false;
        String wordRemove = words.get(0);
        return dictionary.get(key).remove(wordRemove);
    }

    public static ArrayList<String> getWord(String key){
        if(dictionary.containsKey(key)) {
            return new ArrayList<>(dictionary.get(key));
        } else {
            return new ArrayList<>();
        }
    }

    public static void setWord(ArrayList<String> words){
        String key = words.get(0);
        words.remove(0);
        if(!dictionary.containsKey(key)){
            dictionary.put(key,new HashSet<>(words));
            return;
        }

        dictionary.get(key).addAll(words);
    }
}
