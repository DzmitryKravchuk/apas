package com.belpost.apas.service;

import com.belpost.apas.mapper.OfficeMapper;
import com.belpost.apas.model.OfficeModel;
import com.belpost.apas.model.common.Node;
import com.belpost.apas.persistence.entity.Office;
import com.belpost.apas.persistence.repository.OfficeRepository;
import com.belpost.apas.service.common.NodeServiceImpl;
import com.belpost.apas.service.util.OfficeServiceHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class OfficeServiceImpl extends NodeServiceImpl<Office, OfficeModel> implements OfficeService {

    private final OfficeTypeService officeTypeService;

    public OfficeServiceImpl(OfficeRepository repository,
                             OfficeMapper mapper,
                             OfficeTypeService officeTypeService) {
        super(repository, mapper);
        this.officeTypeService = officeTypeService;
    }

    @Override
    @Transactional(readOnly = true)
    public OfficeModel getByCode(String code) {
        log.info("getByCode: {}", code);
        Node<OfficeModel> node = getNode(super.getByCode(code));
        OfficeServiceHelper helper = getOfficeServiceHelper(node);
        helper.setRemainingFields();
        return node.getNodeElement();
    }

    private Node<OfficeModel> getNode(OfficeModel model) {
        return new Node<>(model);
    }

    private OfficeServiceHelper getOfficeServiceHelper(Node<OfficeModel> node) {
        return new OfficeServiceHelper(node, this, officeTypeService);
    }

    @Override
    @Transactional(readOnly = true)
    public OfficeModel getParentById(Long id) {
        log.info("getParentById: {}", id);
        return super.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public OfficeModel getById(Long id) {
        log.info("getById: {}", id);
        Node<OfficeModel> node = getNode(super.getById(id));
        OfficeServiceHelper helper = getOfficeServiceHelper(node);
        helper.setRemainingFields();
        return node.getNodeElement();
    }

    @Override
    @Transactional(readOnly = true)
    public Node<OfficeModel> getAsTree(Long ancestorId) {
        log.info("Get as tree for id: {}", ancestorId);
        Node<OfficeModel> root = super.getAsTree(ancestorId);
        OfficeServiceHelper helper = getOfficeServiceHelper(root);
        helper.setRemainingFields();

        return root;
    }


    @Override
    @Transactional(readOnly = true)
    public Node<OfficeModel> getAsBranch(Long ancestorId, Long targetId) {
        log.info("Get as branch for ancestorId: {} and targetId: {}", ancestorId, targetId);
        Node<OfficeModel> root = super.getAsBranch(ancestorId, targetId);
        OfficeServiceHelper helper = getOfficeServiceHelper(root);
        helper.setRemainingFields();

        return root;
    }

}
