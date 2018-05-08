package com.example.exercises;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class StreamExercisesPart1Test {
    // Level 1

    /**
     *  1) map stream to add one to each element
     */
    @Test
    public void simpleMapping_JustAddOneToCreatedStream() throws Exception {
        List<Integer> start = Arrays.asList(1, 2, 3, 4, 5);

        Stream<Integer> result = start.stream().map(i -> i + 1); // complete this line - add one to each value

        List expected = Arrays.asList(2, 3, 4, 5, 6);
        assertEquals(expected, result.collect(Collectors.toList()));
    }


    /**
     * 1) complete function getName so it returns product name
     */
    @Test
    public void singleFunctionObjectMapping_extractName() throws Exception {
        Function<Product,String> getName= p -> p.name; // complete this, Product class is definied at the bottom of this file

        Stream<String> names = products().map(getName);

        assertThat(names.collect(Collectors.toList())).containsExactly("tv","console","mouse","speakers");
    }

    /**
     *  don't change test - implement 'sum' methods
     *
     */
    @Test
    public void sumOfFirstNIntegers() throws Exception {

        assertThat(sum(5)).isEqualTo(15);
        assertThat(sum(6)).isEqualTo(21);
        assertThat(sum(7)).isEqualTo(28);
    }

    private int sum(int n){
        return IntStream
            .iterate(1, seed -> seed + 1) //<- FIX
            .limit(n)  //<- FIX
            .sum();
    }

    /**
     *  don't change test - implement 'calculateIntegerSum' methods
     *
     */
    @Test
    public void findMaxInt() throws Exception {
        OptionalInt optionalMax = IntStream
            .of(11, 22, 4, 5, 9, 1, 79, 2, 3)
            .max();  // <- FIX

        assertThat(optionalMax).hasValue(79);

    }


    @Test
    public void calculateIntegerSum() throws Exception {
        int sum = Stream.of(
            new BigInteger("10"),
            new BigInteger("20"),
            new BigInteger("30")
        ).mapToInt(bi -> bi.intValue()).sum();  // <- FIX BOTH , change boxed type to int and then replace 'hashCode' method

        assertThat(sum).isEqualTo(60);
    }


    /**
     *  1) extract price which is String
     *  2) convert price to BigDecimal
     */
    @Test
    public void multipleFunctionsMapping_extractPriceAndConvertToBigDecimal() throws Exception {
        Function<Product,String> getPrice= p -> p.price;      //complete this
        Function<String,BigDecimal> toBigDecimal= s -> new BigDecimal(s); //complete this

        Stream<BigDecimal> prices =
            products().map(getPrice).map(toBigDecimal);

        assertThat(prices.collect(Collectors.toList())).containsExactly(
            new BigDecimal("300.0"),new BigDecimal("200.0"),new BigDecimal("20.0"),new BigDecimal("45.5")
        );
    }

    //LEVEL 2
    /**
     *  1) extract price from Product  - > String
     *  2) convert price to BigDecimal
     *  3) sum all prices
     */
    @Test
    public void sumAllPrices_extractPricesChangeToBigDecimalAndReduce() throws Exception {
        Function<Product,String> getPrice= p -> p.price;
        Function<String,BigDecimal> toBigDecimal= s -> new BigDecimal(s);

        Function<Product, BigDecimal> bigDecimalPrice = getPrice.andThen(toBigDecimal);

        BigDecimal result = products().map(bigDecimalPrice).reduce(BigDecimal.ZERO, (sum, price) ->  sum.add(price));

        assertThat(result).isEqualTo(new BigDecimal("565.5"));
    }

    /**
     * 1) write each name to mutable list "names"
     */
    @Test
    public void rewriteWithForeach() throws Exception {
        List<String> names=new LinkedList<>();

        products().forEach(p -> names.add(p.name)); // write each name to names list

        assertThat(names).containsExactly("tv","console","mouse","speakers");
    }


    /**
     *  1) map every element to one
     *  2) reduce by adding all values
     */
    @Test
    public void countNumberOfElementsWithMapReduce() throws Exception {
        Integer numberOfProducts = products()
            .<Integer>map(p -> 0)
            .reduce(0,(no, m) -> no + 1);

        assertThat(numberOfProducts).isEqualTo(4);

    }


    // Level 3

    /**
     *  don't change test
     *  complete basicMapReduce method which is listed below
     */
    @Test
    public void mapReduceTest() throws Exception {
        List<String> input = Arrays.asList("1", "2", "3","4");
        Function<String,Integer> toInt = Integer::parseInt;
        BinaryOperator<Integer> multiply=(i1, i2)->i1*i2;

        Integer result = basicMapReduce(toInt, multiply, 1, input.stream());

        assertThat(result).isEqualTo(24);

    }

    //EXERCISE
    //Use 'map' on Stream and then 'reduce'
    private <A,B> B basicMapReduce(Function<A,B> map, BinaryOperator<B> reduce, B identity, Stream<A> input){
        return input.map(map).reduce(identity, reduce);
    }

    /**
     * don't change test
     *  write map in terms of "forEach" - complete "mapInTermsOfForEach"
     */
    @Test
    public void  mapInTermsOfForEach() throws Exception {
        Collection<String> result = mapInTermsOfForEach(products(), p -> p.name);

        assertThat(result).containsExactly("tv","console","mouse","speakers");
    }

    // EXERCISE
    // you need to add mapped elements to result collection
    private <A,B> Collection<B>  mapInTermsOfForEach(Stream<A> input,Function<A,B> f){
        Collection<B> result=new LinkedList<>();

        input.forEach(a -> ((LinkedList<B>) result).addLast(f.apply(a)));

        return result;
    }

    /**
     *  don't change test
     *  write map in terms of "reduce" - complete "mapInTermsOfReduce"
     */
    @Test
    public void mapsInTermsOfReduce() throws Exception {
        Collection<String> result = mapInTermsOfReduce(products(), p -> p.name);

        assertThat(result).containsExactly("tv","console","mouse","speakers");

    }

    // EXERCISE
    // complete two functions which are used in stream reduce operation
    private <A,B> Collection<B>  mapInTermsOfReduce(Stream<A> input,Function<A,B> f){
        List<B> identity=new LinkedList<>();

        BiFunction<List<B>,A,List<B>> accumulate = (l, e) -> {
            l.add(f.apply(e));
            List<B> lb = new LinkedList<B>();
            lb.addAll(l);
            return lb;
        };

        BinaryOperator<List<B>> combine=(l1,l2)-> {
            List<B> result = new LinkedList<B>();
            result.addAll(l1);
            result.addAll(l2);

            return result;
        };


        return input.reduce(identity,accumulate,combine);
    }



    //Products
    private Stream<Product> products(){
        Product tv=new Product("tv","300.0");
        Product console=new Product("console","200.0");
        Product mouse=new Product("mouse","20.0");
        Product speakers=new Product("speakers","45.5");

        return Arrays.asList(tv,console,mouse,speakers).stream();
    }

    class Product{
        final String name;
        final String price;

        public Product(String name, String price) {
            this.name = name;
            this.price = price;
        }
    }
}
