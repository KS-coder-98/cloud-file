package cloud.file.management.model;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class LambdaExpression {
    public static <T> void consumer(List<T> list, Consumer<T>consumer){
        for(T t : list){
            consumer.accept(t);
        }
    }

    public static <T> void actionIf(List<T> list, Consumer<T>consumer, Predicate<T>predicate){
        for ( T t : list ){
            if ( predicate.test(t) ){
                consumer.accept(t);
            }
        }
    }

    public static <T> T find(List<T> list, Predicate<T> predicate){
        for ( T t : list ){
            if ( predicate.test(t) )
                return t;
        }
        return null;
    }
}
