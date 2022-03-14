package IntersectionOrderApp;

public class KIntervalError extends Exception{
    public KIntervalError(){
        super("<b>Error:</b> Please give k in interval 1 to the length of the intersection.");
    }
}
