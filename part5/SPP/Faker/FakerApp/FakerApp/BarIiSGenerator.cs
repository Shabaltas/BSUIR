using Faker;

namespace FakerApp
{
    public class BarIisGenerator: NumericGenerator<int>
    {
        public override int Generate()
        {
            return Random.Next(-10, -6);
        }
    }
}