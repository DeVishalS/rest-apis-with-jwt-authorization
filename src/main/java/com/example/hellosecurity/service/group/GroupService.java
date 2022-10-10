package com.example.hellosecurity.service.group;

import com.example.hellosecurity.dto.group.GroupDto;
import com.example.hellosecurity.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    Group createGroup(GroupDto groupDto);
    Optional<Group> getGroupById(Long groupId);
    List<Group> getAllGroups();
    Group updateGroup(Group oldGroup, Group newGroup);
    void deleteGroup(Long id);

}
