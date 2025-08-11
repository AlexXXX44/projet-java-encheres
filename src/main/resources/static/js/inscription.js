
//Quels champs ai-je besoin de consulter? (lecture)
let inputs;
//Quels champs ai je besoin de mettre à jour? (écriture)
let infos;

onload = init;

// La function lancée une fois que la page est chargée
function init(){
    //récupération de tous les input de inscription.html dans le tableau
    inputs = document.getElementsByTagName("input");

    // //récupération de tous les textes qui passeront en rouge si erreur
    infos = document.getElementsByTagName("p");

    inputs.confirmMdp.addEventListener("input", verifierCmdp);

    let formulaire = document.getElementById("inscription-form");

}
function verifierCmdp(){

    let mdp = inputs.motDePasse.value;
    let confirmMdp = inputs.confirmMdp.value;

        if(mdp === confirmMdp){
            infos.cmdpInfo.style.color = "green";
            infos.cmdpInfo.textContent = "Mot de passe confirmé";
            document.getElementById("creer").disabled = false;
        }else{
            infos.cmdpInfo.style.color = "red";
            infos.cmdpInfo.textContent = "Erreur de saisie !";
            document.getElementById("creer").disabled = true;
        }
}