package wooteco.subway.admin.domain.graph;

import java.util.List;
import java.util.Objects;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import wooteco.subway.admin.domain.entity.Station;

public class SubwayShortestPath {
	private final GraphPath shortestPath;

	public SubwayShortestPath(GraphPath shortestPath) {
		this.shortestPath = shortestPath;
	}

	public static SubwayShortestPath of(Graph<Long, SubwayEdge> subwayGraph, Station source, Station target) {
		DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(subwayGraph);
		GraphPath shortestPath = dijkstraShortestPath.getPath(source.getId(), target.getId());

		if (Objects.isNull(shortestPath)) {
			throw new PathNotFoundException(PathNotFoundException.PATH_NOT_FOUND_MESSAGE);
		}

		return new SubwayShortestPath(shortestPath);
	}

	public int calculateTotalDistance() {
		List<SubwayEdge> edgeList = this.shortestPath.getEdgeList();
		return edgeList.stream()
			.mapToInt(SubwayEdge::getDistance)
			.sum();
	}

	public int calculateTotalDuration() {
		List<SubwayEdge> edgeList = this.shortestPath.getEdgeList();
		return edgeList.stream()
			.mapToInt(SubwayEdge::getDuration)
			.sum();
	}

	public List<Long> getShortestPath() {
		return shortestPath.getVertexList();
	}
}
