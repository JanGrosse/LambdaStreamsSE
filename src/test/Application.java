package test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Application {
    // demo
    public boolean isPrime(int n) {
        if (n % 2 == 0)
            return false;

        for (int i = 3;i * i <=n;i+= 2)
            if (n % i == 0)
                return false;

        return true;
    }

    @Test
    public void test01() {
        assertEquals("1284131 is prime",true,isPrime(1284131));
    }

    @Test
    public void test02() {
        assertEquals("1284051 is composite",false,isPrime(1284051));
    }
}