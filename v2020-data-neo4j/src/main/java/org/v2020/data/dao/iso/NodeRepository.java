package org.v2020.data.dao.iso;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.v2020.data.entity.Node;

public interface NodeRepository extends CrudRepository<Node, Long> {
    
    <N extends Node> N findByTitle(String title);
    
    <N extends Node> N save(N node);

    /* Cypher Query example
    @Query("START n=node(*) MATCH (n)-[:CHILD]-c WHERE id(n)= {0} RETURN n")
    <N extends Node> N loadWithChildren(Long id);
    */
}
