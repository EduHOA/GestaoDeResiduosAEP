package com.ecoapp.waste_management.dto.collectionpoint;

import jakarta.validation.constraints.*;

import java.util.List;

public class CreateCollectionPointDTO {
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String name;

    @NotBlank(message = "Endereço é obrigatório")
    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    private String address;

    @NotNull(message = "Latitude é obrigatória")
    @DecimalMin(value = "-90.0", message = "Latitude deve ser entre -90 e 90")
    @DecimalMax(value = "90.0", message = "Latitude deve ser entre -90 e 90")
    private Double latitude;

    @NotNull(message = "Longitude é obrigatória")
    @DecimalMin(value = "-180.0", message = "Longitude deve ser entre -180 e 180")
    @DecimalMax(value = "180.0", message = "Longitude deve ser entre -180 e 180")
    private Double longitude;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String phone;

    @Size(max = 100, message = "Horário de funcionamento deve ter no máximo 100 caracteres")
    private String openingHours;

    @Positive(message = "Capacidade deve ser positiva")
    private Integer capacity;

    private List<Long> acceptedWasteIds;

    // Getters e Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getOpeningHours() { return openingHours; }
    public void setOpeningHours(String openingHours) { this.openingHours = openingHours; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public List<Long> getAcceptedWasteIds() { return acceptedWasteIds; }
    public void setAcceptedWasteIds(List<Long> acceptedWasteIds) { this.acceptedWasteIds = acceptedWasteIds; }
}