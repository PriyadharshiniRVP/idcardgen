async function loadStudents() {

    const response = await fetch("http://localhost:8080/idcard");

    const students = await response.json();

    const tableBody = document.getElementById("studentTable");

    tableBody.innerHTML = "";

    students.forEach(student => {

        tableBody.innerHTML += `
            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.department}</td>
                <td>${student.city}</td>
                <td>${student.photopath}</td>
            </tr>
        `;
    });
}

loadStudents();