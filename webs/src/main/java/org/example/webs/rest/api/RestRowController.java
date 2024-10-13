package org.example.webs.rest.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.webs.database.Result;
import org.example.webs.rest.exceptions.ApiException;
import org.example.webs.rest.service.DatabaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestRowController {
    private final DatabaseService databaseService;

    @GetMapping(value = "/rows/select-by-conditions")
    @Operation(description = "Select by conditions from DB")
    public ResponseEntity<Result> selectCondition(@RequestParam String columns,
                                                  @RequestParam String tableName,
                                                  @RequestParam String condition) throws ApiException {
        final var database = databaseService.getDatabase();
        return ResponseEntity.ok(database.query(String.format("select %s from %s where %s", columns, tableName, condition)));
    }

    @GetMapping(value = "/rows/select")
    @Operation(description = "Select from DB without conditions")
    public ResponseEntity<Result> select(@RequestParam String columns,
                                         @RequestParam String tableName) throws ApiException {
        final var database = databaseService.getDatabase();
        return ResponseEntity.ok(database.query(String.format("select %s from %s", columns, tableName)));
    }

    @PostMapping(value = "/rows/insert")
    @Operation(description = "Insert into table")
    public ResponseEntity<Result> insert(@RequestParam String columns,
                                         @RequestParam String tableName,
                                         @RequestParam String values) throws ApiException {
        final var database = databaseService.getDatabase();
        return ResponseEntity.ok(database.query(String.format("insert into %s (%s) values (%s)", tableName, columns, values)));
    }

    @DeleteMapping(value = "/rows/delete")
    @Operation(description = "Delete from DB by condition")
    public ResponseEntity<Result> delete(@RequestParam String tableName,
                                         @RequestParam String condition) throws ApiException {
        final var database = databaseService.getDatabase();
        return ResponseEntity.ok(database.query(String.format("delete from %s where %s", tableName, condition)));
    }
}