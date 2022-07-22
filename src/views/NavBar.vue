<!-- This example requires Tailwind CSS v2.0+ -->
<template>
    <header :class="{ dark: ui.dark }" class="text-gray-800 dark:text-gray-200 
     opacity-80 
     bg-gray-400 dark:bg-[#000428]
     rounded-full">
        <Disclosure as="nav">
            <div class="max-w-7xl mx-auto px-2 sm:px-6 lg:px-8">
                <div class="relative flex w-full border-b items-center justify-between h-10">

                    <!-- LOGO -->
                    <div class="hover:bg-gray-400 hover:rounded-full hover:w-20 hover:pl-2 hover:h-8 ">
                        <a v-if="ui.dark" href="/">
                            <img width="60" src="../assets/logo_trans_white.png" />
                        </a>
                        <a v-else href="/">
                            <img h width="60" src="../assets/logo_tran.png" />
                        </a>
                    </div>

                    <!-- Navigation -->
                    <div
                        class="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">

                        <!-- Notification -->
                        <button v-if="ui.isLoggedIn" type="button" class="bg-[#321443] p-1 rounded-full ring-1
                            text-[#ffffff] dark:text-gray-200  dark:bg-gray-700
                            hover:text-[#aba921] dark:hover:text-white
                            focus:outline-none  focus:ring-offset-1 focus:ring-offset-[#aba921] dark:focus:ring-offset-gray-50
                            focus:ring-gray-600 dark:focus:ring-gray-50">
                            <BellIcon class="h-4 w-4" aria-hidden="false" />
                        </button>

                        <!-- Language -->
                        <button type="button" class="ml-2">
                            <svg class="w-8 h-6 fill-[#321443] dark:fill-white" viewBox="1 1 49 31" fill="none"
                                style="border:1px solid yellow; height: inherit; border-radius: 5px;"
                                xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M34.3 8.49219C35.1422 8.49219 35.8312 9.10918 35.8312 10.0156V10.3203H40.425C41.2672 10.3203 41.9562 10.9373 41.9562 11.8438C41.9562 12.6816 41.2672 13.3672 40.425 13.3672H40.2719L40.1494 13.71C39.468 15.5076 38.4267 17.2596 37.1098 18.6916C37.1788 18.7297 37.2477 18.7068 37.3166 18.8135L38.7636 19.6742C39.4909 20.1084 39.7206 21.0453 39.2842 21.7689C38.8555 22.4926 37.9138 22.7211 37.1864 22.2869L35.7394 21.4262C35.4025 21.2205 34.9967 21.0072 34.7364 20.7787C33.9325 21.35 33.0597 21.8451 32.1409 22.2564L31.8577 22.3783C31.0844 22.7211 30.1809 22.3707 29.8364 21.6014C29.4919 20.832 29.8441 19.9332 30.6173 19.5904L30.893 19.4686C31.383 19.2477 31.8577 18.9354 32.3094 18.7221L31.383 17.7928C30.7781 17.1986 30.7781 16.2389 31.383 15.6447C31.9802 15.043 32.9448 15.043 33.542 15.6447L34.6598 16.7492L34.7058 16.7264C35.6552 15.7971 36.4284 14.6393 36.9873 13.2986H28.7875C27.8764 13.2986 27.2563 12.6816 27.2563 11.7752C27.2563 10.9373 27.8764 10.2518 28.7875 10.2518H32.7687V9.94707C32.7687 9.10918 33.3889 8.42363 34.3 8.42363V8.49219ZM12.25 13.7633L13.7047 17.0234H10.7264L12.25 13.7633ZM0 5.75C0 3.05732 2.19352 0.875 4.9 0.875H44.1C46.8027 0.875 49 3.05732 49 5.75V25.25C49 27.9389 46.8027 30.125 44.1 30.125H4.9C2.19352 30.125 0 27.9389 0 25.25V5.75ZM24.5 25.25H44.1V5.75H24.5V25.25ZM13.6511 9.39863C13.4061 8.85019 12.8548 8.49219 12.25 8.49219C11.6452 8.49219 11.0939 8.85019 10.8489 9.39863L5.95044 20.3674C5.60744 21.0682 5.95503 22.0355 6.72831 22.3783C7.50083 22.7211 8.40656 22.3707 8.75109 21.6014L9.4325 20.0018H15.0675L15.7489 21.6014C16.0934 22.3707 16.9969 22.7211 17.7702 22.3783C18.5434 22.0355 18.8956 21.0682 18.5511 20.3674L13.6511 9.39863Z" />
                            </svg>
                        </button>

                        <!-- Theme Toggle -->
                        <button type="button" class="ml-2">
                            <svg class=" h-6 w-14" viewBox="0 0 189 88" version="1.1" xmlns="http://www.w3.org/2000/svg"
                                xmlns:xlink="http://www.w3.org/1999/xlink" :class="{ dark: ui.dark }"
                                @click="ui.switch()">
                                <!-- Generator: Sketch 63.1 (92452) - https://sketch.com -->
                                <defs>
                                    <circle id="path-1" cx="25" cy="25" r="25"></circle>
                                </defs>
                                <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <g id="dark-mode-toggle">
                                        <rect id="bg" fill="#364050" fill-rule="nonzero" x="0" y="0" width="189"
                                            height="88" rx="44"></rect>
                                        <circle id="main-circle" fill="#FFFFFF" fill-rule="nonzero" cx="46" cy="44"
                                            r="25">
                                        </circle>
                                        <g id="moon-accents" transform="translate(21.000000, 19.000000)">
                                            <mask id="mask-2" fill="white">
                                                <use xlink:href="#path-1"></use>
                                            </mask>
                                            <use id="Mask" fill-rule="nonzero" xlink:href="#path-1"></use>
                                            <circle id="moon-accent-2" fill="#EEEEEE" fill-rule="nonzero"
                                                mask="url(#mask-2)" cx="44" cy="23" r="11"></circle>
                                            <circle id="moon-accent-1" fill="#EEEEEE" fill-rule="nonzero"
                                                mask="url(#mask-2)" cx="9" cy="8" r="7"></circle>
                                            <circle id="moon-accent-3" fill="#EEEEEE" fill-rule="nonzero"
                                                mask="url(#mask-2)" cx="9" cy="38" r="10"></circle>
                                            <circle id="moon-accent-4" fill="#EEEEEE" fill-rule="nonzero"
                                                mask="url(#mask-2)" cx="24" cy="15" r="6"></circle>
                                            <circle id="moon-accent-5" fill="#EEEEEE" fill-rule="nonzero"
                                                mask="url(#mask-2)" cx="31.5" cy="39.5" r="3.5"></circle>
                                        </g>
                                        <g id="stars" transform="translate(89.000000, 18.000000)" fill="#FFFFFF">
                                            <polygon id="Star"
                                                points="6.5 9.75 2.67939586 11.7586105 3.40906632 7.50430523 0.318132644 4.49138954 4.58969793 3.87069477 6.5 0 8.41030207 3.87069477 12.6818674 4.49138954 9.59093368 7.50430523 10.3206041 11.7586105">
                                            </polygon>
                                            <polygon id="Star-Copy"
                                                points="21 43 15.1221475 46.0901699 16.2447174 39.545085 11.4894348 34.9098301 18.0610737 33.954915 21 28 23.9389263 33.954915 30.5105652 34.9098301 25.7552826 39.545085 26.8778525 46.0901699">
                                            </polygon>
                                            <polygon id="Star-Copy-2"
                                                points="52 8 49.648859 9.23606798 50.097887 6.61803399 48.1957739 4.76393202 50.8244295 4.38196601 52 2 53.1755705 4.38196601 55.8042261 4.76393202 53.902113 6.61803399 54.351141 9.23606798">
                                            </polygon>
                                            <polygon id="Star-Copy-3"
                                                points="60 31 56.4732885 32.854102 57.1468305 28.927051 54.2936609 26.145898 58.2366442 25.572949 60 22 61.7633558 25.572949 65.7063391 26.145898 62.8531695 28.927051 63.5267115 32.854102">
                                            </polygon>
                                        </g>
                                        <g id="clouds" transform="translate(24.000000, 17.000000)" fill="#FFFFFF"
                                            fill-rule="nonzero">
                                            <path
                                                d="M22.4581401,8.64610504 C22.458794,8.60836037 22.4595787,8.57068044 22.4595787,8.53280628 C22.4595787,4.92485595 19.5054963,2 15.8614938,2 C12.7211834,2 10.0935887,4.17216375 9.42701247,7.08174056 C8.80548929,6.7372476 8.08954429,6.53997828 7.32658453,6.53997828 C4.9459644,6.53997828 3.01613657,8.45077798 3.01613657,10.8077806 C3.01613657,10.9041169 3.02051764,10.9993526 3.02686038,11.0941351 C1.2595244,11.743693 0,13.42796 0,15.4038903 C0,17.9422356 2.07832643,20 4.64203597,20 L21.1974387,20 C24.402092,20 27,17.4277945 27,14.2548467 C27,11.5105566 25.0565712,9.2160949 22.4581401,8.64610504 Z"
                                                id="Path"></path>
                                            <path
                                                d="M75.4581401,38.646105 C75.458794,38.6083604 75.4595787,38.5706804 75.4595787,38.5328063 C75.4595787,34.9248559 72.5054963,32 68.8614938,32 C65.7211834,32 63.0935887,34.1721638 62.4270125,37.0817406 C61.8054893,36.7372476 61.0895443,36.5399783 60.3265845,36.5399783 C57.9459644,36.5399783 56.0161366,38.450778 56.0161366,40.8077806 C56.0161366,40.9041169 56.0205176,40.9993526 56.0268604,41.0941351 C54.2595244,41.743693 53,43.42796 53,45.4038903 C53,47.9422356 55.0783264,50 57.642036,50 L74.1974387,50 C77.402092,50 80,47.4277945 80,44.2548467 C80,41.5105566 78.0565712,39.2160949 75.4581401,38.646105 Z"
                                                id="Path-Copy-2"></path>
                                            <path
                                                d="M37.4466786,38.851297 C37.1439054,38.851297 36.8417385,38.8815517 36.5450288,38.9418581 C36.3212839,37.8199968 35.5979024,36.8630135 34.5822578,36.3450283 C33.5668154,35.8270431 32.3708814,35.8053165 31.3372484,36.2859402 C30.4018448,33.6974356 27.5548062,32.3609484 24.978202,33.3006723 C22.4015977,34.2403961 21.071255,37.1007872 22.0066586,39.6890888 C19.7496033,39.7607661 17.9667177,41.6375741 18.0004714,43.905866 C18.0344273,46.1743611 19.8726934,47.9963451 22.1309613,48 L37.4466786,48 C39.9612326,48 42,45.9520193 42,43.4256485 C42,40.8992777 39.9612326,38.851297 37.4466786,38.851297 Z"
                                                id="Path"></path>
                                            <path
                                                d="M56.585009,4.2909511 C56.357929,4.2909511 56.1313039,4.31313789 55.9087716,4.3573626 C55.7409629,3.5346643 55.1984268,2.83287655 54.4366934,2.45302076 C53.6751116,2.07316494 52.778161,2.05723213 52.0029363,2.4096895 C51.3013836,0.511452738 49.1661047,-0.468637832 47.2336515,0.22049299 C45.3011983,0.909623811 44.3034412,3.00724393 45.0049939,4.9053318 C43.3122025,4.95789514 41.9750383,6.33422103 42.0003536,7.99763509 C42.0258205,9.66119812 43.40452,10.9973197 45.098221,11 L56.585009,11 C58.4709244,11 60,9.4981475 60,7.64547555 C60,5.79280364 58.4709244,4.2909511 56.585009,4.2909511 Z"
                                                id="Path-Copy"></path>
                                        </g>
                                    </g>
                                </g>
                            </svg>
                        </button>

                        <!-- Register&Login -->
                        <div v-if="!ui.isLoggedIn">

                            <Menu as="div" class="ml-2 mr-1 relative">
                                <div class="relative inset-y-0 left-0 flex items-center sm:hidden ">
                                    <!-- Mobile menu button-->
                                    <MenuButton 
                                        class="inline-flex items-center justify-center p-2 rounded-md text-gray-700 dark:text-gray-400 hover:text-white hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white">
                                        <span class="sr-only">Open main menu</span>
                                        <MenuIcon v-if="!open"  class="block h-6 w-6" aria-hidden="true" />
                                        <XIcon v-else  class="block h-6 w-6" aria-hidden="false" />
                                    </MenuButton>
                                </div>
                                <div class="flex-1 flex items-center justify-end sm:items-stretch z-50">
                                    <div class="hidden sm:block sm:ml-6">
                                        <div class="flex space-x-4">
                                            <router-link to="/login" @click="ui.showLogin()">
                                                Login
                                            </router-link>
                                            <router-link to="/register" @click="ui.showRegistrationPage()">
                                                Register
                                            </router-link>
                                        </div>
                                    </div>
                                </div>
                                <MenuItems class="origin-top-right absolute right-0 mt-2 w-48 rounded-md  py-1 z-50
                                    bg-white ring-1 ring-black ring-opacity-5 focus:outline-none ">
                                    <MenuItem v-slot="{ active }">
                                    <router-link to="/login" @click="ui.showLogin()"
                                        :class="[active ? 'bg-gray-100' : '', 'flex px-4 py-2 text-sm text-gray-700']">
                                        Login </router-link>
                                    </MenuItem>
                                    <MenuItem v-slot="{ active }">
                                    <router-link to="/register" @click="ui.showRegistrationPage()"
                                        :class="[active ? 'bg-gray-100' : '', 'flex px-4 py-2 text-sm text-gray-700']">
                                        Register
                                    </router-link>
                                    </MenuItem>
                                </MenuItems>
                            </Menu>
                        </div>

                        <!-- Profile dropdown -->
                        <div v-else>
                            <Menu as="div" class="ml-2 mr-1 relative">
                                <div>
                                    <MenuButton class="bg-gradient-to-r from-[#000428] to-[#004E92] 
                                    flex px-1 text-sm rounded-full focus:outline-none focus:ring-2 
                                    focus:ring-offset-2 focus:ring-offset-gray-800 focus:ring-white">
                                        <svg class="h-8 w-6" viewBox="0 0 45 45" fill="none"
                                            xmlns="http://www.w3.org/2000/svg">
                                            <g clip-path="url(#clip0_1_30)">
                                                <path
                                                    d="M22.5321 22.6902C26.3452 22.6902 29.4356 19.5992 29.4356 15.7866C29.4356 11.974 26.3452 8.88301 22.5321 8.88301C18.7189 8.88301 15.6285 11.974 15.6285 15.7866C15.6285 19.5992 18.7189 22.6902 22.5321 22.6902ZM25.2665 25.279H19.7976C14.6366 25.279 10.4508 29.4643 10.4508 34.6258C10.4508 35.6581 11.2879 36.4957 12.3202 36.4957H32.745C33.7773 36.4973 34.6133 35.6613 34.6133 34.6258C34.6133 29.4643 30.428 25.279 25.2665 25.279Z"
                                                    fill="white" />
                                            </g>
                                            <circle cx="22.3249" cy="22.414" r="20.8153" stroke="white"
                                                stroke-width="2" />
                                            <defs>
                                                <clipPath id="clip0_1_30">
                                                    <rect width="24.1625" height="27.6143" fill="white"
                                                        transform="translate(10.4508 8.88301)" />
                                                </clipPath>
                                            </defs>
                                        </svg>
                                        <span class="text-gray-200 p-1 font-semibold hidden sm:flex">{{ name }}</span>
                                    </MenuButton>
                                </div>
                                <transition enter-active-class="transition ease-out duration-10"
                                    enter-from-class="transform opacity-0 scale-95"
                                    enter-to-class="transform opacity-100 scale-100"
                                    leave-active-class="transition ease-in duration-100"
                                    leave-from-class="transform opacity-100 scale-100"
                                    leave-to-class="transform opacity-0 scale-95">
                                    <MenuItems class="origin-top-right absolute right-0 mt-2 w-48 rounded-md  py-1 
                                    bg-white ring-1 ring-black ring-opacity-5 focus:outline-none ">
                                        <!-- Explore -->
                                        <MenuItem v-slot="{ active }">
                                        <router-link to="/explore" :class="[active ? 'bg-gray-100' : '',
                                        'flex px-4 py-2 text-sm text-gray-700']">
                                            <svg class="h-6 w-6 mr-2" viewBox="0 0 36 36" fill="none"
                                                xmlns="http://www.w3.org/2000/svg">
                                                <path
                                                    d="M19.8545 18C19.8545 19.2445 18.8687 20.25 17.6484 20.25C16.4282 20.25 15.4424 19.2445 15.4424 18C15.4424 16.7555 16.4282 15.75 17.6484 15.75C18.8687 15.75 19.8545 16.7555 19.8545 18ZM0 18C0 8.05781 7.90043 0 17.6484 0C27.3964 0 35.2969 8.05781 35.2969 18C35.2969 27.9422 27.3964 36 17.6484 36C7.90043 36 0 27.9422 0 18ZM22.4121 21.5648L26.2383 11.4187C26.7553 9.99141 25.4386 8.71172 24.1011 9.23906L14.1532 13.1414C13.5672 13.3664 13.1053 13.8375 12.8847 14.4352L9.05861 24.5813C8.54157 25.9453 9.79626 27.2883 11.1957 26.7609L21.1437 22.8586C21.7296 22.6336 22.1915 22.1625 22.4121 21.5648Z"
                                                    fill="#E9ACFF" />
                                            </svg>

                                            Explore
                                        </router-link>
                                        </MenuItem>

                                        <!-- Subscription -->
                                        <MenuItem v-slot="{ active }">
                                        <router-link to="/subscriptions" :class="[active ? 'bg-gray-100' : '',
                                        'flex px-4 py-2 text-sm text-gray-700']">
                                            <svg class="h-6 w-6 mr-2" viewBox="0 0 45 28" fill="none"
                                                xmlns="http://www.w3.org/2000/svg">
                                                <path
                                                    d="M0 22.993L4.5 22.9958C5.74805 22.9958 6.75 21.991 6.75 20.7437V5.0211H0V22.993ZM3.375 18.507C3.99023 18.507 4.5 19.0075 4.5 19.6313C4.5 20.246 3.99023 20.7556 3.375 20.7556C2.75977 20.7556 2.25 20.2438 2.25 19.632C2.25 19.0063 2.75977 18.507 3.375 18.507ZM24.525 0.500004C23.9666 0.500004 23.4239 0.708762 23.0119 1.08557L16.0945 7.41172C16.0875 7.42579 16.0875 7.43985 16.0734 7.43985C14.9062 8.53672 14.9273 10.2875 15.9258 11.3773C16.8223 12.2984 18.6947 12.6163 19.8724 11.5707C19.8844 11.5602 19.8914 11.5602 19.8984 11.5531L25.515 6.41329C25.972 6.00048 26.6927 6.02685 27.1055 6.4836C27.5273 6.94035 27.4922 7.65219 27.0352 8.07407L25.1986 9.75243L35.4375 18.057C35.6396 18.2325 35.8242 18.4083 35.9912 18.6014V4.93672C33.1098 2.05672 29.2075 0.44516 25.135 0.44516L24.525 0.500004ZM23.5266 11.2859L21.4172 13.2188C19.3254 15.125 16.1262 14.9408 14.2594 12.9115C12.375 10.85 12.5227 7.65078 14.5758 5.76641L20.3273 0.500004H19.8633C15.7992 0.500004 11.8898 2.12282 9.01406 4.99297L9 20.7359L10.2832 20.7385L16.6465 26.4915C18.5801 28.0644 21.4102 27.7656 22.9746 25.8414L24.2487 26.913C25.3652 27.7637 27.0176 27.6512 27.9316 26.5353L30.138 23.8212L30.5159 24.1375C31.4827 24.9109 32.8981 24.7705 33.68 23.8037L34.3506 22.9754C35.1325 22.0086 34.986 20.6002 34.0206 19.8162L23.5266 11.2859ZM38.25 5.02813V20.75C38.25 21.9889 39.252 23.0035 40.4367 23.0035L45 23V5.00704L38.25 5.02813ZM41.625 20.75C41.0098 20.75 40.5 20.2405 40.5 19.6257C40.5 19.0017 41.0098 18.5014 41.625 18.5014C42.2402 18.5014 42.75 19.0063 42.75 19.632C42.75 20.2438 42.2438 20.75 41.625 20.75Z"
                                                    fill="#ACFFB4" />
                                            </svg>

                                            Subscription
                                        </router-link>
                                        </MenuItem>

                                        <!-- Profile -->
                                        <MenuItem v-slot="{ active }">
                                        <router-link to="/profile" :class="[active ? 'bg-gray-100' : '',
                                        'flex px-4 py-2 text-sm text-gray-700']">
                                            <svg class="h-6 w-6 mr-2" viewBox="0 0 32 36" fill="none"
                                                xmlns="http://www.w3.org/2000/svg">
                                                <path
                                                    d="M15.75 18C20.7211 18 24.75 13.9704 24.75 9C24.75 4.02961 20.7211 0 15.75 0C10.7789 0 6.75 4.02961 6.75 9C6.75 13.9704 10.7789 18 15.75 18ZM19.3148 21.375H12.1852C5.45695 21.375 0 26.8312 0 33.5602C0 34.9059 1.09125 35.9979 2.43703 35.9979H29.0644C30.4102 36 31.5 34.9102 31.5 33.5602C31.5 26.8312 26.0438 21.375 19.3148 21.375Z"
                                                    fill="#7CA5F4" />
                                            </svg>
                                            Profile
                                        </router-link>
                                        </MenuItem>

                                        <MenuItem v-slot="{ active }">
                                        <router-link to="/activities" :class="[active ? 'bg-gray-100' : '',
                                        'flex px-4 py-2 text-sm text-gray-700']">
                                            <svg class="h-6 w-6 mr-2" viewBox="0 0 36 36" fill="none"
                                                xmlns="http://www.w3.org/2000/svg">
                                                <path
                                                    d="M22.9711 28.3852C21.4242 28.9406 19.7437 29.25 18 29.25C16.2563 29.25 14.5758 28.9406 13.0289 28.3852C12.9445 28.3781 12.9867 28.3711 12.9727 28.3641C10.8562 27.5906 8.97188 26.3391 7.44609 24.75C4.92469 22.1203 3.375 18.5555 3.375 14.625C3.375 6.5475 9.92109 0 18 0C26.0789 0 32.625 6.5475 32.625 14.625C32.625 18.5555 31.0148 22.1203 28.5539 24.75C28.4836 24.8203 28.4133 24.8977 28.343 24.968C26.8383 26.4656 25.0102 27.6469 22.9711 28.3852ZM16.5867 7.81172V8.29688C16.193 8.38125 15.757 8.50078 15.4688 8.65547C14.4211 9.13359 13.507 10.0195 13.282 11.3063C13.1555 12.0234 13.2258 12.7195 13.5211 13.3453C13.8164 13.9641 14.2734 14.4 14.7375 14.7164C15.5531 15.2719 16.6289 15.5953 17.4516 15.8414L17.6062 15.8836C18.5906 16.1859 19.2516 16.4039 19.6664 16.7063C19.8422 16.8328 19.9055 16.9312 19.9266 16.9945C19.9547 17.0508 19.9969 17.1773 19.9477 17.4586C19.9055 17.7047 19.7719 17.9156 19.3852 18.0773C18.9563 18.2602 18.2602 18.3516 17.3602 18.2109C16.9383 18.1406 16.1859 17.8875 15.518 17.6625C15.3633 17.6062 15.2086 17.557 15.068 17.5078C14.3297 17.2617 13.5352 17.6625 13.2891 18.4008C13.043 19.1391 13.4437 19.9336 14.182 20.1164C14.2664 20.2078 14.3719 20.243 14.4914 20.2852C14.9836 20.475 15.9187 20.7703 16.5867 20.925V21.375C16.5867 22.1555 17.2195 22.7883 17.9367 22.7883C18.7805 22.7883 19.4133 22.1555 19.4133 21.375V20.9883C19.7859 20.918 20.1516 20.7492 20.4891 20.6648C21.6 20.1938 22.4859 19.2797 22.718 17.9438C22.8445 17.2125 22.7883 16.5094 22.507 15.8695C22.2328 15.2367 21.7898 14.7727 21.3188 14.4352C20.468 13.8094 19.3289 13.4719 18.4781 13.2117L18.3586 13.1977C17.4234 12.8953 16.7484 12.6844 16.3195 12.3891C16.1367 12.2625 16.0805 12.1781 16.0664 12.143C16.0523 12.1148 16.0102 12.0305 16.0523 11.7914C16.0805 11.6508 16.1859 11.4188 16.6289 11.2219C17.0227 11.018 17.782 10.9055 18.6398 10.9758C18.9492 11.0883 19.8984 11.2711 20.1727 11.3414C20.918 11.5453 21.6914 11.0953 21.8883 10.343C22.0922 9.59766 21.6422 8.82422 20.8898 8.62734C20.5805 8.54297 19.8773 8.40234 19.4133 8.31797V7.875C19.4133 7.09453 18.7805 6.46172 18 6.46172C17.2195 6.46172 16.5867 7.09453 16.5867 7.875V7.81172ZM3.375 24.75H4.49859C5.86617 26.5711 7.59375 28.1039 9.57656 29.25H4.5V31.5H31.5V29.25H26.4234C28.343 28.1039 30.1359 26.5711 31.5 24.75H32.625C34.4883 24.75 36 26.2617 36 28.125V32.625C36 34.4883 34.4883 36 32.625 36H3.375C1.51102 36 0 34.4883 0 32.625V28.125C0 26.2617 1.51102 24.75 3.375 24.75Z"
                                                    fill="#FFB1CD" />
                                            </svg>

                                            Activities
                                        </router-link>
                                        </MenuItem>

                                        <!-- Logout -->
                                        <MenuItem type="button" v-slot="{ active }" @click="ui.logOut()">
                                        <router-link to="/" :class="[active ? 'bg-gray-100' : '',
                                        'flex px-4 py-2 text-sm text-gray-700']">
                                            <svg class="h-6 w-6 mr-2" viewBox="0 0 36 32" fill="none"
                                                xmlns="http://www.w3.org/2000/svg">
                                                <path
                                                    d="M11.25 27.25H6.75C5.50758 27.25 4.5 26.2424 4.5 25V7C4.5 5.75758 5.50758 4.75 6.75 4.75H11.25C12.4924 4.75 13.5 3.74242 13.5 2.5C13.5 1.25758 12.4945 0.25 11.25 0.25H6.75C3.02203 0.25 0 3.27203 0 7V25C0 28.728 3.02203 31.75 6.75 31.75H11.25C12.4924 31.75 13.5 30.7424 13.5 29.5C13.5 28.2576 12.4945 27.25 11.25 27.25ZM35.3391 14.4109L26.3391 5.41094C25.4595 4.53133 24.0356 4.53273 23.1574 5.41094C22.2785 6.28984 22.2785 7.71367 23.1574 8.59258L28.3219 13.75H13.5C12.2555 13.75 11.25 14.7555 11.25 16C11.25 17.2445 12.2562 18.25 13.5 18.25H28.3219L23.1623 23.4095C22.2834 24.2884 22.2834 25.7123 23.1623 26.5912C24.0413 27.4701 25.4651 27.4701 26.344 26.5912L35.344 17.5912C36.218 16.7102 36.218 15.2898 35.3391 14.4109Z"
                                                    fill="#FFA0A0" />
                                            </svg>

                                            Log out
                                        </router-link>
                                        </MenuItem>
                                    </MenuItems>
                                </transition>
                            </Menu>
                        </div>
                    </div>
                </div>
            </div>

        </Disclosure>
    </header>
</template>

<script lang="ts">
import { Disclosure, DisclosureButton, DisclosurePanel, Menu, MenuButton, MenuItem, MenuItems } from '@headlessui/vue'
import { BellIcon, MenuIcon, XIcon } from '@heroicons/vue/outline'
import { defineComponent, ref, computed } from "vue";
import { uiStore } from "../../stores/uiStore";
import { userStore } from "../../stores/userStore";

export default defineComponent({
    setup() {
        const user = userStore();
        const ui = uiStore();
        const name = 'Mahmud Hasan';
        const open = false;
        const active = false;
        return { ui, name, user, open, active };
    },
    components: {
        Disclosure, DisclosureButton, DisclosurePanel, Menu, MenuButton, MenuItem, MenuItems,
        BellIcon, MenuIcon, XIcon
    },
    methods: {
        toggle() {
            this.open = !this.open;
            console.log(this.open)
        }
    }


});


</script>


<style scoped>
/* ADD TRANSITIONS */
#bg,
#stars,
#clouds,
#main-circle,
#moon-accents {
    transition: all 0.3s ease-out;
}

/* BACKGROUND */
svg:not(.dark) #bg {
    fill: #47cfff;
}

/* SUN STYLES */
svg:not(.dark) #main-circle {
    transform: translateX(100px);
    fill: #fee253;
}

/* HIDE MOON ACCENTS FOR LIGHT */
svg:not(.dark) #moon-accents {
    transform: translate(121px, 19px);
    /* 100 + 21 */
    opacity: 0;
}

/* HIDE STARS ON LIGHT MODE */
svg:not(.dark) #stars {
    opacity: 0;
    transform: translate(69px, 18px);
}

/* HIDE CLOUDS ON DARK MODE */
svg.dark #clouds {
    opacity: 0;
    transform: translate(54px, 17px);
}
</style>