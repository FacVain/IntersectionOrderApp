package IntersectionOrderApp;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class App 
{
    public static int findTheKthSmallestInIntersection(ArrayList<Integer> list1, ArrayList<Integer> list2, int k) throws EmptyListError, KIntervalError
    {
        if(list1.size() == 0 || list2.size() == 0)
            throw new EmptyListError();    

        ArrayList<Integer> list = new ArrayList<>();
        for (Integer i : list1){
            if(!list.contains(i)){
                if(list2.contains(i))
                    list.add(i);
            }
        }

        if(k < 1 || k > list.size())
            throw new KIntervalError();
        
        return kthSmallest(list, 0, list.size() - 1, k);
    }

    public static int kthSmallest(ArrayList<Integer> list, int start, int end, int k)
    {
        int partition = partition(list, start, end);
 
        if (partition == k - 1)
            return list.get(partition);
 
        else if (partition < k - 1)
            return kthSmallest(list, partition + 1, end, k);
 
        else
            return kthSmallest(list, start, partition - 1, k);
    }

    public static int partition(ArrayList<Integer> list, int start, int end)
    {
        int pivot = list.get(end), pivotindex = start;
        for (int i = start; i <= end; i++) 
        {
            if (list.get(i) < pivot) 
            {
                int temp = list.get(i);
                list.set(i, list.get(pivotindex));
                list.set(pivotindex, temp);
                pivotindex++;
            }
        }
 
        int temp = list.get(end);
        list.set(end, list.get(pivotindex));
        list.set(pivotindex, temp);
 
        return pivotindex;
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());

        get("/",
            (rq, rs) -> {
              Map<String, String> map = new HashMap<String, String>();
              map.put("result", "not computed yet!");
              map.put("error", "");
              return new ModelAndView(map, "findTheKth.mustache");
            },
            new MustacheTemplateEngine());

        post("/", (req, res) -> {
            Map<String, String> map = new HashMap<String, String>();
            try
            {  
                String list1 = req.queryParams("list1");
                java.util.Scanner sc1 = new java.util.Scanner(list1);
                sc1.useDelimiter("[;\r\n]+");
                java.util.ArrayList<Integer> inputList1 = new java.util.ArrayList<>();
                while (sc1.hasNext())
                {
                    int value = Integer.parseInt(sc1.next().replaceAll("\\s",""));
                    inputList1.add(value);
                }
                sc1.close();
    
                String list2 = req.queryParams("list2");
                java.util.Scanner sc2 = new java.util.Scanner(list2);
                sc2.useDelimiter("[;\r\n]+");
                java.util.ArrayList<Integer> inputList2 = new java.util.ArrayList<>();
                while (sc2.hasNext())
                {
                    int value = Integer.parseInt(sc2.next().replaceAll("\\s",""));
                    inputList2.add(value);
                }
                sc2.close();
    
                String kstr = req.queryParams("k").replaceAll("\\s","");
                int k = Integer.parseInt(kstr);
    
                int result = App.findTheKthSmallestInIntersection(inputList1, inputList2, k);
                map.put("result", "" + result);
                map.put("error", "");
            }
            catch(NumberFormatException e)
            {
                map.put("result", "not computed yet!");
                map.put("error", "<b>Error:</b> Please enter integer values.");
            }
            catch(EmptyListError | KIntervalError e)
            {
                map.put("result", "not computed yet!");
                map.put("error", e.getMessage());
            }
            catch(Exception e)
            {
                map.put("result", "not computed yet!");
                map.put("error", "<b>Error:</b> An error occured please enter inputs as stated.");
            }
          return new ModelAndView(map, "findTheKth.mustache");
        }, new MustacheTemplateEngine());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}