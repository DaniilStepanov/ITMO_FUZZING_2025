package org.itmo.fuzzing.lect2;

import org.itmo.fuzzing.lect1.SimpleFuzz;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class URLValidator {

    public static boolean httpProgram(String url) throws IllegalArgumentException {
        List<String> supportedSchemes = Arrays.asList("http", "https");
        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            String host = uri.getHost();

            if (scheme == null || !supportedSchemes.contains(scheme)) {
                throw new IllegalArgumentException("Scheme must be one of " + supportedSchemes);
            }
            if (host == null || host.isEmpty()) {
                throw new IllegalArgumentException("Host must be non-empty");
            }

            // Do something with the URL
            return true;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL format", e);
        }
    }

    public static boolean isValidUrl(String url) {
        try {
            httpProgram(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
//        SimpleFuzz sf = new SimpleFuzz();
//        for (int i = 0; i < 100_000_000; i++) {
//            var url = sf.fuzzer(100, 32, 96);
//            try {
//                httpProgram(url);
//                System.out.println("SUCCESS");
//            } catch (Exception e) {
//
//            }
//        }

//
//        //Let's try to mutate url
//        String seed = "http://www.google.com/search?q=fuzzing";
//        var validUrls = 0;
//        var invalidUrls = 0;
//        for (int i = 0; i < 10; i++) {
//            var mutatedSeed = StringMutator.mutate(seed);
//            if (isValidUrl(mutatedSeed)) {
//                validUrls++;
//            } else {
//                invalidUrls++;
//            }
//        }
//        System.out.println("VALID URLS = " + validUrls + " INVALID URLS = " + invalidUrls);

        //Let's try to mutate mutated url

        String seed = "http://www.google.com/search?q=fuzzing";
//        for (int i = 0; i < 50; i++) {
//            seed = StringMutator.mutate(seed);
//            if (i % 5 == 0) {
//                System.out.println("ITERATION " + i + " SEED: " + seed);
//            }
//        }

        //Let's implement MutationFuzzer


//        //What happens if we're applying more than one mutation?
//
        MutationFuzzer mf = new MutationFuzzer(Arrays.asList(seed), 5, 10) {
            @Override
            public String mutate(String input) {
                return StringMutator.mutate(input);
            }
        };
        System.out.println(mf.fuzz());
        System.out.println(mf.fuzz());
        System.out.println(mf.fuzz());

    }
}