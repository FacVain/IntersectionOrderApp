package IntersectionOrderApp;

public class EmptyListError extends Exception{
    public EmptyListError(){
        super("<b>Error:</b> Input lists cannot be empty.");
    }
}
