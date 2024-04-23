# KUBERNETS / K8S
https://devopssec.fr/article/creer-cluster-kubernetes-multi-noeud-vagrant-ansible

Arrive alors en 2014 Kubernetes souvent abrégé k8s, né à partir du projet Borg et Omega. Ces deux derniers ont été développés en tant que systèmes purement internes à Google, à l'inverse de Kubernetes, qui est quant à lui maintenant devenu une version opensource de ses prédécesseurs avec des fonctionnalités améliorées permettant de résoudre les anciens problèmes de gestion des clusters, ainsi qu'un support accru de la part d'IBM, Cisco et Redhat.
De nos jours le projet n'appartient plus vraiment à Google, car Google a fait don du projet Kubernetes en 2015 à la toute récente Cloud Native Computing Foundation.

### Les objets Kubernetes
- **Node** : un node est une machine de travail du cluster Kubernetes. Ce sont des unités de travail qui peuvent être physiques, virtuelles mais aussi des instances cloud.
- **Pod** : Il s'agit de l'unité la plus petite de K8s, un pod encapsule le ou les conteneur(s) formant votre application conteneurisée partageant ainsi la même stack réseau (chaque pod se voit attribuer une adresse IP unique) et le même stockage, plus précisément un volume partagé (tous les conteneurs du pod peuvent accéder aux volumes partagés, ce qui permet à ces conteneurs de partager plus facilement des données).
- **Replicas** : c'est le nombre d'instances d'un Pod
- **ReplicaSet** :  s'assure que les réplicas spécifiés sont actifs
- **Deployment** : défini l'état désiré et fournit des mises à jour déclaratives de vos Pods et ReplicaSets.
- **Service** : Un service peut être défini comme un ensemble logique de pods exposés en tant que service réseau. C'est un niveau d'abstraction au-dessus du pod, qui fournit une adresse IP et un nom DNS unique pour un ensemble de pods. Avec les Services, il est très facile de gérer la configuration de Load Balancing (équilibreur de charge) permettant ainsi aux pods de scaler plus facilement.
- **Endpoint** : Représente l'adresse IP et le port d'un service, il est automatiquement créé lors de la création d'un service avec les pods correspondants.

### Les composants des nodes
Kubernetes suit l’architecture maître-esclave, le maître plus communément appelé master existe principalement pour gérer votre cluster Kubernetes. . Les esclaves sont quant à eux plus connus sous le nom de workers (on les appellent aussi minions ) et ne sont là que pour fournir de la capacité et n'ont pas le pouvoir d'ordonner à une autre node ce qu'il peut ou ne peut pas faire. 

#### Les composants du Master
- **kube-apiserver** : point d'entrée exposant l'API HTTP Rest de k8s depuis le maître du cluster Kubernetes. Différents outils et bibliothèques peuvent facilement communiquer avec l'API.
- **kube-scheduler** : Il est responsable de la répartition et l'utilisation de la charge de travail sur les nœuds du cluster selon les ressources nécessaires et celles disponibles.
- **kube-controller-manager** : ce composant est responsable de la plupart des collecteurs qui récupèrent des informations du cluster tout en effectuant des actions de correctives en cas de besoin, en apportant des modifications pour amener l'état actuel du serveur à l'état souhaité. Il est composé de plusieurs contrôleurs, on peut par exemple retrouver un contrôleur de réplication qui va s'assurer que vous avez le nombre désiré de répliques sur vos pods, mais aussi d'autres contrôleurs clés comme , le contrôleur de Endpoints, le contrôleur d’espace de noms et le contrôleur de compte de service.
- **cloud-controller-manager** :effectue les mêmes actions que le **kube-controller-manager** mais pour des fournisseurs de cloud sous-jacents (AWS, Azure, Google Cloud Platform, etc ...).
- **etcd** : il stocke les informations de configuration pouvant être utilisées par chacun des nœuds du cluster. Ces informations sont conservées sous forme de clé et valeurs à haute disponibilité

#### Les composants des Workers
- **kubelet** : Il s’agit d’un agent qui s'exécute dans chaque nœud chargé de relayer les informations au Master. Il interagit avec la base de données **etcd** du Master pour récupérer des informations afin de connaître les tâches à effectuer. Il assume la responsabilité de maintenir en bon état de fonctionnement les conteneurs d'un pod et s'assure qu'ils tournent conformément à la spécification. Il ne gère pas les conteneurs qui n'ont pas été créés par Kubernetes. Il communique avec le Master et redémarre le conteneur défaillant en cas de crash.
- **kube-proxy** : il active l'abstraction réseau du Service Kubernetes en maintenant les règles du réseau et permet l'exposition des services vers l'extérieur.
- **Environnement d'exécution de conteneurs** : Il faut vous aussi une solution d'exécution d'applications conteneurisées, vous pouvez utiliser soit le moteur Docker mais Kubernetes prend également en charge l'utilisation de rkt comme moteur d'exécution du conteneur.

![image-architecture-kubernetes](kubernetes-cluster-architecture.jpg)

### Prérequis
#### Hyperviseur
Il faut tout d'abord s'assurer que la virtualisation est prise en charge sous notre machine Linux :
```bash
egrep --color 'vmx|svm' /proc/cpuinfo
```
Vérifiez ensuite que la sortie est non vide. Si c'est bien le cas, alors vous pouvez passer à l'étape suivante.
La prochaine étape consiste à installer un hyperviseur. 

#### Kubectl
Il est recommandé au préalable d'installer kubectl, afin d'interagir avec notre cluster Kubernetes.
Pour ce faire, premièrement commençons par installer le binaire kubectl :
```bash
curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl && \ 
chmod +x kubectl
```
Après cela, déplaçons le binaire kubectl dans le dossier d'exécution des utilisateurs :
```bash
sudo mv ./kubectl /usr/local/bin/kubectl
```
Enfin, testons notre installation en vérifiant la version de kubectl :
```bash
kubectl version
```

### Installation de Minikube
Une fois les prérequis satisfaits, on peut à ce moment-là, passer à l'étape d'installation de **Minikube**.
```bash
curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64 \
&& chmod +x minikube
```
Déplaçons ensuite l’exécutable Minikube dans le dossier binaire des utilisateurs :
```bash
sudo mv minikube /usr/local/bin
```
Analysons ensuite le bon déroulement de notre installation en révélant la version de notre Minikube :
```bash
minikube version
```

### Création du cluster Kubernetes avec Minikube
À présent, il est temps de démarrer Minikube afin de créer notre premier cluster Kubernetes. La commande que nous allons exécuter va créer et configurer une machine virtuelle qui exécute un cluster Kubernetes à un seul nœud, elle configurera également notre installation de kubectl de manière à communiquer avec notre cluster.
````bash
minikube start
```
**Résultat**
```bash
😄  minikube v1.2.0 on linux (amd64)
💿  Downloading Minikube ISO ...
    129.33 MB / 129.33 MB [============================================] 100.00% 0s
🔥  Creating virtualbox VM (CPUs=2, Memory=2048MB, Disk=20000MB) ...
🐳  Configuring environment for Kubernetes v1.15.0 on Docker 18.09.6
💾  Downloading kubeadm v1.15.0
💾  Downloading kubelet v1.15.0
🚜  Pulling images ...
🚀  Launching Kubernetes ... 
⌛  Verifying: apiserver proxy etcd scheduler controller dns
🏄  Done! kubectl is now configured to use "minikube"
```

Si vous souhaitez utiliser votre machine physique en tant que noeud, alors utilisez l'option **--vm-driver** de la commande `minikube start` avec la valeur **none**. Cette option exécutera les composants Kubernetes sur votre machine hôte et non sur une machine virtuelle à condition de posséder le moteur Docker sur votre machine.
````bash
minikube start --vm-dirver=none
```

Vous pouvez également personnaliser votre noeud en utilisant certaines options de la commande `minikube start`. Vous pouvez retrouver la configuration complète de Minikube dans le fichier : 
```bash
cat ~/.minikube/machines/minikube/config.json
```
Pour obtenir les noms des champs configurables de notre nœud Minikube, nous exécuterons alors la commande suivante :
```bash
minikube config -h
```

### Manipulation du cluster Kubernetes avec Minikube
Commençons par vérifie l'état de notre cluster :
````bash
minikube stauts
```
On va utiliser l'outil kubectl afin de récupérer la liste des nœuds de notre cluster Kubernetes :
```bash
kubectl get node
```
On va utiliser l'outil kubectl afin de récupérer la liste des nœuds de notre cluster Kubernetes :
```bash
kubectl cluster-info
```
Si jamais, vous rencontrez quelques soucis avec votre cluster Kubernetes, n'hésitez alors pas à fouiller dans les logs de Minikube afin de connaître la source du problème :
```bash
minikube logs
```
Vous n'aurez nullement besoin de vous connecter à la VM Minikube, mais si l'envie vous en dit, alors voici la commande destinée à se connecter directement en ssh à votre nœud Minikube :
```bash
minikube ssh
```
De la même façon, vous pouvez aussi vérifier si votre minikube est à jour de la façon suivante :
```bash
minikube update-check
```
Il existe deux façons pour communiquer avec un cluster Kubernetes, soit comme vu antérieurement, en utilisant le binaire kubectl, soit depuis une interface web de management nommé Dashboard. Ça tombe bien car minikube a pensé à nous, dans l'intention de nous faciliter le lancement d'un Dashboard :
```bash
minikube dashboard
```
Vous n'avez plus besoin de votre cluster Kubernetes, et vous souhaitez vous en débarrasser facilement ? Vous pouvez facilement sans aucune résistance supprimer votre cluster Minikube, à l'aide de la commande suivante :
```bash
minikube delete
```

Minikube est un outil qui facile grandement la gestion d'un cluster Kubernetes. Cependant, cette méthode connait quelques limites, notamment le fait qu'on ne peut déployer qu'un noeud unique qui est à la fois de type Master et Workers.
Pour éviter ce problème, nous allons donc dans cet article mettre en place un cluster Kubernetes multi-nœud. Nous allons automatiser l'aménagement de notre cluster Kubernetes multi-nœud. Pour cela, nous utiliserons l'outil Vagrant et Ansible. D'un côté, vagrant sera utilisé pour créer et provisionner nos machines virtuelles à l'aide de l'hyperviseur Virtualbox et d'un autre côté, Ansible sera utilisé pour installer et configurer l'environnement Kubernetes.



