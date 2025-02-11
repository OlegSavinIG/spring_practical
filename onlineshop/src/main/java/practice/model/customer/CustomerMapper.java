package practice.model.customer;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    private final ModelMapper modelMapper;

    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CustomerResponse toCustomerResponse(Customer customer) {
        return modelMapper.map(customer, CustomerResponse.class);
    }

    public Customer toCustomerEntity(CustomerRequest customerRequest) {
        return modelMapper.map(customerRequest, Customer.class);
    }

    public CustomerRequest toCustomerRequest(Customer customer) {
        return modelMapper.map(customer, CustomerRequest.class);
    }
}

