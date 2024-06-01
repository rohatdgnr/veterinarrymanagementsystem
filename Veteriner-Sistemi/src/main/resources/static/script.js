const API_URL = 'http://localhost:8080/api/v1';

document.addEventListener('DOMContentLoaded', () => {
    const customerList = document.getElementById('customerList');
    const animalList = document.getElementById('animalList');
    const vaccineList = document.getElementById('vaccineList');
    const doctorList = document.getElementById('doctorList');
    const availableDateList = document.getElementById('availableDateList');
    const appointmentList = document.getElementById('appointmentList');

    const customerName = document.getElementById('customerName');
    const customerEmail = document.getElementById('customerEmail');
    const customerPhone = document.getElementById('customerPhone');
    const customerAddress = document.getElementById('customerAddress');
    const customerCity = document.getElementById('customerCity');
    const addCustomerBtn = document.getElementById('addCustomerBtn');
    const updateCustomerBtn = document.getElementById('updateCustomerBtn');
    const deleteCustomerBtn = document.getElementById('deleteCustomerBtn');

    const animalName = document.getElementById('animalName');
    const animalType = document.getElementById('animalType');
    const animalOwner = document.getElementById('animalOwner');
    const addAnimalBtn = document.getElementById('addAnimalBtn');

        const updateAnimalBtn = document.getElementById('updateAnimalBtn');
        const deleteAnimalBtn = document.getElementById('deleteAnimalBtn');

    const vaccineName = document.getElementById('vaccineName');
    const vaccineAnimal = document.getElementById('vaccineAnimal');
    const addVaccineBtn = document.getElementById('addVaccineBtn');

        const updateVaccineBtn = document.getElementById('updateVaccineBtn');
        const deleteVaccineBtn = document.getElementById('deleteVaccineBtn');

    const doctorName = document.getElementById('doctorName');
    const addDoctorBtn = document.getElementById('addDoctorBtn');

        const updateDoctorBtn = document.getElementById('updateDoctorBtn');
        const deleteDoctorBtn = document.getElementById('deleteDoctorBtn');

    const availableDate = document.getElementById('availableDate');
    const availableDoctor = document.getElementById('availableDoctor');
    const addAvailableDateBtn = document.getElementById('addAvailableDateBtn');
    const updateAvailableDateBtn = document.getElementById('updateAvailableDateBtn');
    const deleteAvailableDateBtn = document.getElementById('deleteAvailableDateBtn');

    const appointmentDate = document.getElementById('appointmentDate');
    const appointmentAnimal = document.getElementById('appointmentAnimal');
    const appointmentDoctor = document.getElementById('appointmentDoctor');
    const addAppointmentBtn = document.getElementById('addAppointmentBtn');
    const updateAppointmentBtn = document.getElementById('updateAppointmentBtn');
    const deleteAppointmentBtn = document.getElementById('deleteAppointmentBtn');

    // Fetch and display data
    const fetchData = async () => {
        await fetchAndDisplayCustomers();
        await fetchAndDisplayAnimals();
        await fetchAndDisplayVaccines();
        await fetchAndDisplayDoctors();
        await fetchAndDisplayAvailableDates();
        await fetchAndDisplayAppointments();
    };

    const fetchAndDisplayCustomers = async () => {
        const response = await fetch(`${API_URL}/customers`);
        const customers = await response.json();
        customerList.innerHTML = '';
        customers.forEach(customer => {
            const li = document.createElement('li');
            li.innerHTML = `
                ${customer.name} (${customer.email})
                <button class="update" data-id="${customer.id}">Update</button>
                <button class="delete" data-id="${customer.id}">Delete</button>
            `;
            customerList.appendChild(li);
        });
        addUpdateDeleteListeners(customerList, 'customers', fetchAndDisplayCustomers);
    };

    const fetchAndDisplayAnimals = async () => {
        const response = await fetch(`${API_URL}/animals`);
        const animals = await response.json();
        animalList.innerHTML = '';
        animals.forEach(animal => {
            const li = document.createElement('li');
            li.innerHTML = `
                ${animal.name} (${animal.type})
                <button class="update" data-id="${animal.id}">Update</button>
                <button class="delete" data-id="${animal.id}">Delete</button>
            `;
            animalList.appendChild(li);
        });
        addUpdateDeleteListeners(animalList, 'animals', fetchAndDisplayAnimals);
    };

    const fetchAndDisplayVaccines = async () => {
        const response = await fetch(`${API_URL}/vaccines`);
        const vaccines = await response.json();
        vaccineList.innerHTML = '';
        vaccines.forEach(vaccine => {
            const li = document.createElement('li');
            li.innerHTML = `
                ${vaccine.name} (Animal ID: ${vaccine.animalId})
                <button class="update" data-id="${vaccine.id}">Update</button>
                <button class="delete" data-id="${vaccine.id}">Delete</button>
            `;
            vaccineList.appendChild(li);
        });
        addUpdateDeleteListeners(vaccineList, 'vaccines', fetchAndDisplayVaccines);
    };

    const fetchAndDisplayDoctors = async () => {
        const response = await fetch(`${API_URL}/doctors`);
        const doctors = await response.json();
        doctorList.innerHTML = '';
        doctors.forEach(doctor => {
            const li = document.createElement('li');
            li.innerHTML = `
                ${doctor.name}
                <button class="update" data-id="${doctor.id}">Update</button>
                <button class="delete" data-id="${doctor.id}">Delete</button>
            `;
            doctorList.appendChild(li);
        });
        addUpdateDeleteListeners(doctorList, 'doctors', fetchAndDisplayDoctors);
    };

    const fetchAndDisplayAvailableDates = async () => {
        const response = await fetch(`${API_URL}/available_dates`);
        const availableDates = await response.json();
        availableDateList.innerHTML = '';
        availableDates.forEach(availableDate => {
            const li = document.createElement('li');
            li.innerHTML = `
                ${availableDate.date} (Doctor ID: ${availableDate.doctorId})
                <button class="update" data-id="${availableDate.id}">Update</button>
                <button class="delete" data-id="${availableDate.id}">Delete</button>
            `;
            availableDateList.appendChild(li);
        });
        addUpdateDeleteListeners(availableDateList, 'available_dates', fetchAndDisplayAvailableDates);
    };

    const fetchAndDisplayAppointments = async () => {
        const response = await fetch(`${API_URL}/appointments`);
        const appointments = await response.json();
        appointmentList.innerHTML = '';
        appointments.forEach(appointment => {
            const li = document.createElement('li');
            li.innerHTML = `
                ${appointment.date} (Animal ID: ${appointment.animalId}, Doctor ID: ${appointment.doctorId})
                <button class="update" data-id="${appointment.id}">Update</button>
                <button class="delete" data-id="${appointment.id}">Delete</button>
            `;
            appointmentList.appendChild(li);
        });
        addUpdateDeleteListeners(appointmentList, 'appointments', fetchAndDisplayAppointments);
    };

    const addUpdateDeleteListeners = (listElement, entityType, fetchFunction) => {
        listElement.querySelectorAll('.update').forEach(button => {
            button.addEventListener('click', async (e) => {
                const id = e.target.getAttribute('data-id');
                const newValue = prompt(`Enter new value for ${entityType} (JSON format):`);
                if (newValue) {
                    await fetch(`${API_URL}/${entityType}/${id}`, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: newValue
                    });
                    fetchFunction();
                }
            });
        });

        listElement.querySelectorAll('.delete').forEach(button => {
            button.addEventListener('click', async (e) => {
                const id = e.target.getAttribute('data-id');
                await fetch(`${API_URL}/${entityType}/${id}`, {
                    method: 'DELETE'
                });
                fetchFunction();
            });
        });
    };

    addCustomerBtn.addEventListener('click', async () => {
        const name = customerName.value;
        const email = customerEmail.value;
        if (name && email) {
            await fetch(`${API_URL}/customers`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ name, email })
            });
            customerName.value = '';
            customerEmail.value = '';
            fetchAndDisplayCustomers();
        }
    });

    addAnimalBtn.addEventListener('click', async () => {
        const name = animalName.value;
        const type = animalType.value;
        const ownerId = animalOwner.value;
        if (name && type && ownerId) {
            await fetch(`${API_URL}/animals`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ name, type, ownerId })
            });
            animalName.value = '';
            animalType.value = '';
            animalOwner.value = '';
            fetchAndDisplayAnimals();
        }
    });

    addVaccineBtn.addEventListener('click', async () => {
        const name = vaccineName.value;
        const animalId = vaccineAnimal.value;
        if (name && animalId) {
            await fetch(`${API_URL}/vaccines`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ name, animalId })
            });
            vaccineName.value = '';
            vaccineAnimal.value = '';
            fetchAndDisplayVaccines();
        }
    });

    addDoctorBtn.addEventListener('click', async () => {
        const name = doctorName.value;
        if (name) {
            await fetch(`${API_URL}/doctors`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ name })
            });
            doctorName.value = '';
            fetchAndDisplayDoctors();
        }
    });

    addAvailableDateBtn.addEventListener('click', async () => {
        const date = availableDate.value;
        const doctorId = availableDoctor.value;
        if (date && doctorId) {
            await fetch(`${API_URL}/available_dates`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ date, doctorId })
            });
            availableDate.value = '';
            availableDoctor.value = '';
            fetchAndDisplayAvailableDates();
        }
    });

    addAppointmentBtn.addEventListener('click', async () => {
        const date = appointmentDate.value;
        const animalId = appointmentAnimal.value;
        const doctorId = appointmentDoctor.value;
        if (date && animalId && doctorId) {
            await fetch(`${API_URL}/appointments`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ date, animalId, doctorId })
            });
            appointmentDate.value = '';
            appointmentAnimal.value = '';
            appointmentDoctor.value = '';
            fetchAndDisplayAppointments();
        }
    });

    fetchData();
});