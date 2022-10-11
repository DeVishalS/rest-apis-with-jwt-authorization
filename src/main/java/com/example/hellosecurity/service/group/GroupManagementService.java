package com.example.hellosecurity.service.group;

import com.example.hellosecurity.dto.group.GroupDto;
import com.example.hellosecurity.model.Group;
import com.example.hellosecurity.repository.GroupRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupManagementService implements GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupManagementService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group createGroup(GroupDto groupDto){
        Group group = new Group();
        BeanUtils.copyProperties(groupDto, group);
        return groupRepository.save(group);
    }

    @Override
    public Optional<Group> getGroupById(Long groupId) {
        return groupRepository.findById(groupId);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group updateGroup(Group oldGroup, GroupDto newGroup) {
        BeanUtils.copyProperties(newGroup,oldGroup);
        return groupRepository.save(oldGroup);
    }

    @Override
    public void deleteGroupById(Long id) {
        groupRepository.deleteById(id);
    }
}
