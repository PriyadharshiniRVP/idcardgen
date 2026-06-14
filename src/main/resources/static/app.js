async function loadStudents() {
     const response = await fetch("http://localhost:8081/idcard");
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
                 <td>${student.photopath || 'No photo'}</td>
                 <td>
                     <button class="idcard-btn" onclick="generateIdCard(${student.id})">
                         🪪 Generate ID Card
                     </button>
                 </td>
             </tr>
         `;
     });
 }

 async function generateIdCard(id) {
     try {
         const response = await fetch(`http://localhost:8081/idcard/${id}/idcard`);

         if (!response.ok) {
             throw new Error('Failed to generate ID card');
         }

         const idCardHtml = await response.text();
         const idCardWindow = window.open('', '_blank');
         idCardWindow.document.write(idCardHtml);
         idCardWindow.document.close();

     } catch (error) {
         console.error('Error generating ID card:', error);
         alert('Failed to generate ID card. Please try again.');
     }
 }

 loadStudents();