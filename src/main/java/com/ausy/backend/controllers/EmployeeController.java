package com.ausy.backend.controllers;


import com.ausy.backend.Exceptions.ErrorResponse;
import com.ausy.backend.Mapper.EmployeeMapper;
import com.ausy.backend.Models.DAO.Employee;
import com.ausy.backend.Models.DTO.EmployeeDTO;
import com.ausy.backend.Services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {


    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeMapper employeeMapper;


    @Operation(summary = "Extrage toti angajatii")
    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getAllEmployees");
        List<Employee> employeeList = null;
        try {
            employeeList = employeeService.findAll();
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeList);
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeList);

    }

    @Operation(summary = "Extrage toti angajatii in format filtrat")
    @GetMapping("/getAllEmployeesDTO")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesDTO() {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        List<Employee> employeeList = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Resolve", "getAllEmployees");

        try {
            employeeList = employeeService.findAll();
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
        }
        employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @Operation(summary = "Adauga un angajat cu un departament si un jobcategory specificat.")
    @PostMapping("addEmployee/{department}/{jobcategory}")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee, @PathVariable int department, @PathVariable int jobcategory) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "addEmployeeDepJob");
        Employee employeeResp = null;
        try {
            employeeResp = employeeService.addEmployee(employee, department, jobcategory);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(employeeResp);

    }

    @Operation(summary = "Sterge un angajat dupa un id.")
    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "deleteEmployee");
        try {
            employeeService.deleteEmployee(id);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("Not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Deleted.");
    }

    @Operation(summary = "Extrage un angajat in format filtrat dupa un id")
    @GetMapping("/getEmployeeDTO/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeDTO(@PathVariable int id) {
        EmployeeDTO employeeDTO = null;
        Employee employee = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployeeDTO");
        try {
            employee = employeeService.findEmployeeById(id);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        employeeDTO = employeeMapper.convertEmployeeToDto(employee);
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(employeeDTO);
    }

    @Operation(summary = "Extrage un angajat dupa un id in format neflitrat.")
    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") int id) {
        Employee employee = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployee");
        try {
            employee = employeeService.findEmployeeById(id);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(employee);
    }

    @Operation(summary = "Update unui angajat")
    @PutMapping("/updateEmployee/{employeeId}/{departmentId}/{jobCategoryId}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable int employeeId, @PathVariable int departmentId, @PathVariable int jobCategoryId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "updateEmployee");
        Employee employeeUpdated;
        try {
            employeeUpdated = employeeService.updateEmployee(employee, employeeId, departmentId, jobCategoryId);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).headers(httpHeaders).body(employeeUpdated);
    }


    @Operation(summary = "Extrage toti angajatii unui departament")
    @GetMapping("/getEmployeesByDepartment/{departmentid}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeByDep(@PathVariable int departmentid) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployeesByDep");
        List<Employee> employeeList;
        List<EmployeeDTO> employeeDTOList;

        try {
            employeeList = employeeService.findEmployeeByDepartment(departmentid);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        employeeDTOList = employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @Operation(summary = "Extrage toti angajatii cu jobul specificat.")
    @GetMapping("/getEmployeesByJob/{jobid}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeByJob(@PathVariable int jobid) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployeesByJob");
        List<Employee> employeeList;
        List<EmployeeDTO> employeeDTOList;

        try {
            employeeList = employeeService.findEmployeeByJobId(jobid);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        employeeDTOList = employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @Operation(summary = "Extrage toti angajatii unui departament cu un job dat.")
    @GetMapping("/getEmployeesByDepartmentAndJob/{departmentid}/{jobid}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeByDepandJob(@PathVariable int departmentid, @PathVariable int jobid) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployeesByDepartmentAndJobId");
        List<Employee> employeeList;
        List<EmployeeDTO> employeeDTOList;

        try {
            employeeList = employeeService.findEmployeesByDepAndJob(jobid, departmentid);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        employeeDTOList = employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @Operation(summary = "Extrage toti angajatii sortati dupa salariu.")
    @GetMapping("/getEmployeesOrderBySalary")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesOrderBySalary() {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        List<Employee> employeeList = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Resolve", "getEmployeesOrderBySalary");

        try {
            employeeList = employeeService.findEmployeesOrderBySalary();
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
        }
        employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @Operation(summary = "Extrage toti angajatii unui departament si ii sorteaza dupa salariu.")
    @GetMapping("/getEmployeesByDepartmentOrderBySalary/{departmentId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartmentOrderBySalary(@PathVariable int departmentId) {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        List<Employee> employeeList = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Resolve", "getEmployeesByDepartmentOrderBySalary");

        try {
            employeeList = employeeService.findEmployeesByDepartmentOrderBySalary(departmentId);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
        }
        employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @Operation(summary = "Seteaza managerul unei liste de angajati.")
    @PutMapping("/setManagerForEmployees/{employees}/{manager}")
    public ResponseEntity<List<Employee>> setManagerForEmployees(@PathVariable List<Integer> employees, @PathVariable int manager) {
        List<Employee> employeeList = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "setManagerForEmployees");
        try {
            employeeList = employeeService.setManager(employees, manager);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(employeeList);
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeList);
    }

    @Operation(summary = "Extrage toti angajatii subordonati unui manager.")
    @GetMapping("getAllEmployeesByManagerId/{managerid}")
    public ResponseEntity<List<Employee>> getAllEmployeesByManagerId(@PathVariable int managerid) {
        List<Employee> employeeList = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getAllEmployeeByManagerId");
        try {
            employeeList = employeeService.findEmployeesByManager(managerid);
        } catch (RuntimeException e) {
            ErrorResponse.LogError(new ErrorResponse("Manager not found!", 404));
            ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeList);
        }

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeList);
    }

    @Operation(summary = "Extrage toti angajatii subordonati unui manager in format filtrat.")
    @GetMapping("getAllEmployeesDTOByManagerId/{managerid}")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeesDTOByManagerId(@PathVariable int managerid) {
        List<Employee> employeeList = null;
        List<EmployeeDTO> employeeDTOS = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getAllEmployeeByManagerId");
        try {
            employeeList = employeeService.findEmployeesByManager(managerid);
        } catch (RuntimeException e) {
            ErrorResponse.LogError(new ErrorResponse("Manager not found!", 404));
            ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeList);
        }
        employeeDTOS = employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOS);
    }

    @Operation(summary = "Seteaza poza de profil a unui angajat.")
    @PutMapping("/setProfilePictureToEmployee/{employeeid}")
    public void uploadImage(@RequestParam("file") MultipartFile imageFile, @PathVariable int employeeid) {
        try {
            byte[] img = imageFile.getBytes();
            System.out.println(img);
            employeeService.setProfilePicture(employeeid, img);
            System.out.println("Image saved");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}