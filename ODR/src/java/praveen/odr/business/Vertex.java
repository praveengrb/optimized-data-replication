package praveen.odr.business;

/**
 *
 * @author Praveen Sankarasubramanian
 */
class Vertex implements Comparable<Vertex> {

    public final String name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;

    public Vertex(String argName) {
        name = argName;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }
}

class Edge {

    public final Vertex target;
    public final double weight;

    public Edge(Vertex argTarget, double argWeight) {
        target = argTarget;
        weight = argWeight;
    }
}
