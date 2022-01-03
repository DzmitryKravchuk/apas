package com.belpost.apas.service;

import com.belpost.apas.mapper.OfficeMapper;
import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.model.OfficeTypeModel;
import com.belpost.apas.model.common.LookupModel;
import com.belpost.apas.model.common.Node;
import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.persistence.repository.common.NodeRepository;
import com.belpost.apas.service.common.LookupService;
import com.belpost.apas.service.common.NodeService;
import com.belpost.apas.service.common.NodeServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class OfficeServiceImpl extends
    NodeServiceImpl<Office, OfficeModel> implements LookupService<OfficeModel>, NodeService<OfficeModel> {

    private final OfficeTypeServiceImpl officeTypeService;
    private final OfficeMapper mapper;

    public OfficeServiceImpl(NodeRepository<Office> repository,
                             OfficeMapper mapper,
                             OfficeTypeServiceImpl officeTypeService) {
        super(repository, mapper);
        this.officeTypeService = officeTypeService;
        this.mapper = mapper;
    }

    @Override
    public OfficeModel getByCode(String code) {
        log.info("getByCode: {}", code);
        //OfficeModel lm = findByCode(code);
        //return getOfficeModel(lm);
        return null;
    }

    private OfficeModel getOfficeModel(LookupModel e) {
        //OfficeTypeModel type = officeTypeService.getById(e.getId());
        //String officeTypeCode = type.getCode();
        //String parentOfficeCode = findById(e.getParentId()).getCode();
        //Integer hierarchyLvl = type.getHierarchyLvl();
        //mapper.mapToModel(e, officeTypeCode, parentOfficeCode, hierarchyLvl);
        return null;
    }

    @Override
    public OfficeModel getById(Long id) {
        log.info("getById: {}", id);
        Office e = findById(id);
        //return getOfficeModel(e);
        return null;
    }

    @Override
    public List<OfficeModel> getAll() {
        log.info("getAll()");
        List<Office> ol = findAll();
        return getOfficeModels(ol);
    }

    private List<OfficeModel> getOfficeModels(List<Office> ol) {
        List<OfficeTypeModel> tl = officeTypeService.getAll();
        return ol.stream()
            .map(e -> convertFindCodes(e, ol, tl))
            .collect(Collectors.toList());
    }

    private OfficeModel convertFindCodes(Office e, List<Office> ol, List<OfficeTypeModel> tl) {
        Map<Long, String> officeTypeCodeMap = tl.stream()
            .collect(Collectors.toMap(OfficeTypeModel::getId, OfficeTypeModel::getCode));

        Map<Long, Integer> hierarchyMap = tl.stream()
            .collect(Collectors.toMap(OfficeTypeModel::getId, OfficeTypeModel::getHierarchyLvl));

        Map<Long, String> officeParentCodeMap = ol.stream()
            .collect(Collectors.toMap(Office::getId, Office::getCode));

        return mapper.mapToModel(e,
            officeTypeCodeMap.get(e.getOfficeTypeId()),
            officeParentCodeMap.get(e.getParentId()),
            hierarchyMap.get(e.getOfficeTypeId())
        );
    }


    @Override
    public Node<OfficeModel> buildNodeTree(OfficeModel root) {
        Node<OfficeModel> rootNode = new Node<>(root);
        List<OfficeModel> descendents = getOfficeModels((findDescendents(root.getId())));

        // create list of nodes
        List<Node<OfficeModel>> descNodes = descendents.stream()
            .map(Node::new)
            .collect(Collectors.toList());

        // add children to parent nodes
        descNodes.forEach(n -> n.addChildren(getChildByParentId(n.getNodeElement().getId(), descNodes)));

        // add children for root node
        List<Node<OfficeModel>> children = descNodes.stream()
            .filter(n -> n.getNodeElement().getParentId().equals(rootNode.getNodeElement().getId()))
            .collect(Collectors.toList());

        rootNode.addChildren(children);

        return rootNode;
    }


    private List<Node<OfficeModel>> getChildByParentId(Long id, List<Node<OfficeModel>> descNodes) {
        return descNodes.stream().
            filter(n -> n.getNodeElement().getParentId().equals(id))
            .collect(Collectors.toList());
    }


}
