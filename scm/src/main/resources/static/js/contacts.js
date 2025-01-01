console.log("Contacts.js");
const baseURL = "http://localhost:8080";

const viewContactModal = document.getElementById('view_contact_modal')
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'view_contact_modal',
  override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal() {
    contactModal.show();
}

function closeContactModal() {
    contactModal.hide();
}

async function loadContactData(id) {
    console.log(id);
    try {
        const data = await (
            await fetch(`${baseURL}/api/contacts/${id}`)
        ).json();
        console.log(data);

        document.querySelector('#contact_name').innerHTML = data.name;
        document.querySelector('#contact_email').innerHTML = "E-mail: <span class='text-blue-500'>" + data.email + "</span>";
         // Update the profile picture
            const contactPicture = document.getElementById("contact_picture");
            contactPicture.src = data.picture || "https://toppng.com/uploads/preview/instagram-default-profile-picture-11562973083brycehrmyv.png";
            contactPicture.alt = `${data.name}'s image` || "Contact picture";
        document.querySelector('#contact_phoneNumber').innerHTML = "Phone Number: <span class='text-blue-500'>" + data.phoneNumber + "</span>";
        document.querySelector('#contact_address').innerHTML = "Contact Address: <span class='text-gray-900 dark:text-gray-200'>" + data.address + "</span>";
        document.querySelector('#contact_description').innerHTML = `Contact Description: <span class='text-gray-800 dark:text-gray-200'>${data.description || "Nil"}</span>`;
        document.querySelector('#contact_websiteLink').innerHTML = `Website Link: <a href="${data.websiteLink || ''}" class='text-blue-500'>${data.websiteLink || 'Nil'}</a>`;
        document.querySelector('#contact_linkedInLink').innerHTML = `LinkedIn Link: <a href="${data.linkedInLink || 'Nil'}" class='text-blue-500'>${data.linkedInLink || 'Nil'}</a>`;
        openContactModal();
    } catch (error) {
        console.log("Error", error);
    }

}

// Delete Contact
async function deleteContact(id) {
    Swal.fire({
      title: "Are you sure?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!"
    }).then((result) => {
      if (result.isConfirmed) {
//        Swal.fire({
//          title: "Deleted!",
//          text: "Your file has been deleted.",
//          icon: "success",
//          confirmButtonColor: "#28a745", // Green button color
//            customClass: {
//              confirmButton: 'swal2-confirm swal2-styled' // Apply custom styles if needed
//            }
//        });
        const url = `${baseURL}/user/contacts/delete/` + id;
        window.location.replace(url);
      }
    });
}