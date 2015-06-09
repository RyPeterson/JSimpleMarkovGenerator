package tests;

import com.peterson.markovchain.TraversableMarkovChain;
import org.junit.Before;

/**
 * @author Peterson, Ryan
 *         Created: 6/9/15
 */
public class TraverseableChainTest extends BasicMarkovTest
{
    @Before
    @Override
    public void setUp()
    {
        markovChain = new TraversableMarkovChain();
    }
}
