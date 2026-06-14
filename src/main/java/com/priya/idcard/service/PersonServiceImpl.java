package com.priya.idcard.service;

import com.priya.idcard.model.Person;
import com.priya.idcard.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(Long id, Person updatedPerson) {
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));

        existingPerson.setName(updatedPerson.getName());
        existingPerson.setDepartment(updatedPerson.getDepartment());
        existingPerson.setCity(updatedPerson.getCity());
        existingPerson.setPhotopath(updatedPerson.getPhotopath());

        return personRepository.save(existingPerson);
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public String generateIdCardHtml(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));

        return buildIdCardHtml(person);
    }

    private String buildIdCardHtml(Person person) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>ID Card - %s</title>
                <style>
                    * {
                        margin: 0;
                        padding: 0;
                        box-sizing: border-box;
                    }
                    
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        min-height: 100vh;
                        padding: 20px;
                    }
                    
                    .id-card {
                        width: 450px;
                        background: white;
                        border-radius: 20px;
                        box-shadow: 0 20px 60px rgba(0,0,0,0.3);
                        overflow: hidden;
                        animation: slideIn 0.5s ease-out;
                    }
                    
                    @keyframes slideIn {
                        from {
                            transform: translateY(-50px);
                            opacity: 0;
                        }
                        to {
                            transform: translateY(0);
                            opacity: 1;
                        }
                    }
                    
                    .card-header {
                        background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);
                        color: white;
                        padding: 30px;
                        text-align: center;
                    }
                    
                    .card-header h1 {
                        font-size: 24px;
                        margin-bottom: 5px;
                    }
                    
                    .card-header p {
                        opacity: 0.9;
                        font-size: 14px;
                    }
                    
                    .photo-section {
                        text-align: center;
                        margin-top: -40px;
                        margin-bottom: 20px;
                    }
                    
                    .photo-circle {
                        width: 100px;
                        height: 100px;
                        background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);
                        border-radius: 50%%;
                        display: inline-flex;
                        align-items: center;
                        justify-content: center;
                        font-size: 50px;
                        border: 4px solid white;
                        box-shadow: 0 5px 15px rgba(0,0,0,0.2);
                    }
                    
                    .card-body {
                        padding: 20px 30px 30px;
                    }
                    
                    .info-row {
                        margin-bottom: 20px;
                        border-bottom: 2px solid #f0f0f0;
                        padding-bottom: 10px;
                    }
                    
                    .label {
                        font-size: 11px;
                        color: #667eea;
                        text-transform: uppercase;
                        font-weight: bold;
                        letter-spacing: 1px;
                    }
                    
                    .value {
                        font-size: 18px;
                        color: #333;
                        margin-top: 5px;
                        font-weight: 500;
                    }
                    
                    .card-footer {
                        background: #f8f9fa;
                        padding: 15px;
                        text-align: center;
                        font-size: 11px;
                        color: #666;
                    }
                    
                    .button-container {
                        text-align: center;
                        padding: 20px;
                        background: white;
                        margin-top: 20px;
                    }
                    
                    button {
                        background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);
                        color: white;
                        border: none;
                        padding: 12px 24px;
                        border-radius: 8px;
                        font-size: 14px;
                        cursor: pointer;
                        margin: 0 5px;
                        transition: transform 0.2s, box-shadow 0.2s;
                    }
                    
                    button:hover {
                        transform: translateY(-2px);
                        box-shadow: 0 5px 15px rgba(0,0,0,0.2);
                    }
                    
                    @media print {
                        body {
                            background: white;
                        }
                        .button-container {
                            display: none;
                        }
                        .id-card {
                            box-shadow: none;
                        }
                    }
                </style>
            </head>
            <body>
                <div>
                    <div class="id-card">
                        <div class="card-header">
                            <h1>🎓 STUDENT ID CARD</h1>
                            <p>Valid for Academic Year 2024-25</p>
                        </div>
                        <div class="photo-section">
                            <div class="photo-circle">
                                %s
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="info-row">
                                <div class="label">Student ID</div>
                                <div class="value">#%d</div>
                            </div>
                            <div class="info-row">
                                <div class="label">Full Name</div>
                                <div class="value">%s</div>
                            </div>
                            <div class="info-row">
                                <div class="label">Department</div>
                                <div class="value">%s</div>
                            </div>
                            <div class="info-row">
                                <div class="label">City</div>
                                <div class="value">%s</div>
                            </div>
                        </div>
                        <div class="card-footer">
                            <p>This card is property of ID Card System</p>
                            <p>If found, please return to administration</p>
                        </div>
                    </div>
                    <div class="button-container">
                        <button onclick="window.print()">🖨️ Print / Save as PDF</button>
                        <button onclick="window.close()">❌ Close</button>
                    </div>
                </div>
            </body>
            </html>
            """,
                person.getName(),
                person.getPhotopath() != null && !person.getPhotopath().isEmpty() ? "📸" : "👤",
                person.getId(),
                person.getName(),
                person.getDepartment(),
                person.getCity()
        );
    }

}