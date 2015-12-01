package org.v2020.data.dao.iso;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.v2020.data.entity.Node;

public interface NodeRepository extends GraphRepository<Node> {
    
    <N extends Node> N findByTitle(String title);
    
    @Query("START n=node(*) MATCH (n) WHERE n.className={0} RETURN n")
    List<Node> findByClass(String nodeType);
}
