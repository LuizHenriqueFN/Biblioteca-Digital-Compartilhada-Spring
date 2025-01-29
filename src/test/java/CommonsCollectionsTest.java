import org.apache.commons.collections4.ListUtils;
import java.util.Arrays;
import java.util.List;

public class CommonsCollectionsTest {
    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5, 6);

        List<Integer> mergedList = ListUtils.union(list1, list2);
        System.out.println("Listas combinadas: " + mergedList);
    }
}
