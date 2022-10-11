package com.example.hellosecurity.controller.group;

import com.example.hellosecurity.dto.group.GroupDto;
import com.example.hellosecurity.model.Group;
import com.example.hellosecurity.service.group.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Group> getGroupById(@PathVariable("id") Long id) {
        return groupService.getGroupById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody GroupDto groupDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.createGroup(groupDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGroupById(@PathVariable("id") Long id) {
        try {
            groupService.deleteGroupById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Group> updateGroupByGivenId(@NotNull @PathVariable("id") Long id, @RequestBody GroupDto group) {
        Optional<Group> oldGroupEntity = groupService.getGroupById(id);
        if (oldGroupEntity.isPresent()) {
            Group updatedGroup = groupService.updateGroup(oldGroupEntity.get(), group);
            return ResponseEntity.ok(updatedGroup);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
