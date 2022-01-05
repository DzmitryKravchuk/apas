package com.belpost.apas.service.common;

import com.belpost.apas.mapper.common.NodeMapper;
import com.belpost.apas.model.common.Node;
import com.belpost.apas.model.common.NodeModel;
import com.belpost.apas.persistence.entity.common.LookupEntity;
import com.belpost.apas.persistence.entity.common.NodeEntity;
import com.belpost.apas.persistence.repository.common.NodeRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

public abstract class NodeServiceImpl<E extends NodeEntity, M extends NodeModel>
    extends LookupServiceImpl<E, M> implements NodeService<M>{

    private final NodeRepository<E> repository;
    private final NodeMapper<M, E> mapper;

    public NodeServiceImpl(NodeRepository<E> repository, NodeMapper<M, E> mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Node<M> getAsTree(Long ancestorId) {
        M rootElement = getById(ancestorId);
        Node<M> rootNode = new Node<>(rootElement);

        List<M> descendents = findDescendents(ancestorId);

        // create list of nodes
        List<Node<M>> descNodes = descendents.stream()
            .map(Node::new)
            .collect(Collectors.toList());

        // add children to parent nodes
        descNodes.forEach(n -> n.addChildren(getChildByParentId(n.getNodeElement().getId(), descNodes)));

        // add children for root node
        List<Node<M>> children = descNodes.stream()
            .filter(n -> n.getNodeElement().getParentId().equals(rootNode.getNodeElement().getId()))
            .collect(Collectors.toList());

        rootNode.addChildren(children);

        return rootNode;
    }

    private List<M> findDescendents(Long ancestorId) {
        Set<Long> ancestorIds = new HashSet<>(Collections.singleton(ancestorId));
        Set<Long> parentIds = new HashSet<>(Collections.singleton(ancestorId));
        while (!repository.findAllByParentIdIn(parentIds.toArray(new Long[0])).isEmpty()) {
            // get all children for next generation of parents
            List<E> children = repository.findAllByParentIdIn(parentIds.toArray(new Long[0]));
            parentIds = children.stream()
                .map(LookupEntity::getId).collect(Collectors.toSet());
            // add parent ids to ancestor id list
            ancestorIds.addAll(parentIds);
        }

        return repository.findAllByParentIdIn(ancestorIds.toArray(new Long[0])).stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());
    }

    private List<Node<M>> getChildByParentId(Long id, List<Node<M>> descNodes) {
        return descNodes.stream().
            filter(n -> n.getNodeElement().getParentId().equals(id))
            .collect(Collectors.toList());
    }

    //TODO delete when not necessary
    public void printTree(Node<M> node, String appender) {
        System.out.println(appender + node.getNodeElement().getCode());
        node.getChildren().forEach(each -> printTree(each, appender + appender));
    }

    @Override
    public List<M> convertToList(Node<M> node) {
        List<M> l = new ArrayList<>();
        getModelsFromNode(node,l);
        return l;
    }

    private void getModelsFromNode(Node<M> node, List<M> l){
        l.add(node.getNodeElement());
        node.getChildren().forEach(each -> getModelsFromNode(each, l));
    }

    @Override
    public Node<M> getAsBranch(Long ancestorId, Long nodeId){

        return  null;
    }
}
