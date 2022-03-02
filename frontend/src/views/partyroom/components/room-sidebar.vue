<template>
	<div
		class="absolute flex top-0 h-screen z-20 left-0 flex-row-reverse border-r border-main-100"
	>
		<!-- toggle button -->
		<!-- <button
			@click.prevent="toggle"
			class="w-6 h-48 p-0 my-auto rounded-r-full cursor-pointer text-white bg-main-200 text-center focus:outline-none hover:bg-gray-500 transition-color duration-300"
		>
			<svg
				v-if="isSidebarOpen"
				class="p-0"
				viewBox="0 0 48 48"
				fill="none"
				xmlns="http://www.w3.org/2000/svg"
			>
				<rect width="40" height="40" fill="white" fill-opacity="0.01" />
				<path
					d="M31 36L19 24L31 12"
					stroke="black"
					stroke-width="4"
					stroke-linecap="round"
					stroke-linejoin="round"
				/>
			</svg>
			<svg
				v-else
				class="p-0"
				viewBox="0 0 48 48"
				fill="none"
				xmlns="http://www.w3.org/2000/svg"
			>
				<rect width="40" height="40" fill="white" fill-opacity="0.01" />
				<path
					d="M19 12L31 24L19 36"
					stroke="black"
					stroke-width="4"
					stroke-linecap="round"
					stroke-linejoin="round"
				/>
			</svg>
		</button> -->

		<!-- sidebar -->
		<div
			ref="content"
			class="transition-all py-8 w-32 duration-700 bg-main-300 overflow-hidden flex flex-col items-center justify-evenly"
			:class="[isSidebarOpen ? 'max-w-lg' : 'max-w-0']"
		>
			<!-- contents button -->
			<transition name="slide-fade">
				<div
					class="hover:bg-sub-300 w-full cursor-pointer"
					v-show="showContentBtn"
					@click="clickContentBtn"
				>
					<a class="h-24 px-6 flex flex-col justify-center items-center w-full">
						<svg
							version="1.1"
							class="sidebar-menu-icon h-8 w-8"
							xmlns="http://www.w3.org/2000/svg"
							xmlns:xlink="http://www.w3.org/1999/xlink"
							x="0px"
							y="0px"
							viewBox="0 0 477.518 477.518"
							style="enable-background: new 0 0 477.518 477.518"
							xml:space="preserve"
						>
							<path
								d="M436.161,165.291c-38.291-78.37-79.318-83.845-117.516-65.166c-11.446,5.598-21.283,12.768-28.5,22.115H187.38
              c-7.208-9.347-17.046-16.517-28.492-22.115C120.69,81.446,79.664,86.921,41.373,165.291C3.05,243.645-18.007,365.237,20.19,383.915
              c23.481,11.472,63.875-18.681,100.418-61.776c10.48,19.3,30.926,32.402,54.431,32.402c30.166,0,55.278-21.584,60.77-50.148h5.9
              c5.491,28.564,30.604,50.148,60.77,50.148c23.396,0,43.755-12.984,54.279-32.135c38.942,44.139,78.89,72.1,100.556,61.509
              C495.541,365.237,474.451,243.645,436.161,165.291z M132.775,236.974c0,0.699-0.217,1.336-0.397,1.975
              c-0.731,2.612-2.752,4.556-5.404,5.21c-0.569,0.124-1.113,0.342-1.727,0.342h-5.467h-19.293c-4.16,0-7.528-3.375-7.528-7.527
              v-15.197H77.749c-4.16,0-7.526-3.375-7.526-7.527v-24.76c0-4.152,3.367-7.528,7.526-7.528H92.96v-15.225
              c0-4.152,3.368-7.528,7.528-7.528h19.293h5.467c4.16,0,7.527,3.376,7.527,7.528v15.225h15.211c4.16,0,7.528,3.376,7.528,7.528v24.76
              c0,4.152-3.368,7.527-7.528,7.527h-15.211V236.974z M175.039,329.971c-20.615,0-37.327-16.712-37.327-37.327
              c0-20.615,16.712-37.328,37.327-37.328c20.615,0,37.327,16.712,37.327,37.328C212.367,313.259,195.654,329.971,175.039,329.971z
              M302.479,329.971c-20.616,0-37.328-16.712-37.328-37.327c0-20.615,16.712-37.328,37.328-37.328
              c20.615,0,37.328,16.712,37.328,37.328C339.806,313.259,323.094,329.971,302.479,329.971z M337.153,216.195
              c-7.907,0-14.308-6.425-14.308-14.34c0-7.885,6.401-14.31,14.308-14.31c7.917,0,14.317,6.425,14.317,14.31
              C351.47,209.771,345.069,216.195,337.153,216.195z M371.89,244.657c-1.626,0.637-3.367,1.058-5.217,1.058
              c-0.296,0-0.545-0.156-0.841-0.172c-6.671-0.404-11.882-5.333-12.994-11.788c-0.132-0.793-0.475-1.524-0.475-2.363
              c0-7.901,6.393-14.324,14.31-14.324c1.85,0,3.591,0.419,5.217,1.058c5.311,2.083,9.091,7.216,9.091,13.266
              C380.981,237.439,377.201,242.556,371.89,244.657z M371.89,185.618c-1.626,0.639-3.367,1.057-5.217,1.057
              c-7.917,0-14.31-6.423-14.31-14.324c0-7.901,6.393-14.324,14.31-14.324c1.85,0,3.591,0.42,5.217,1.058
              c5.311,2.085,9.091,7.217,9.091,13.266C380.981,178.401,377.201,183.519,371.89,185.618z M396.191,216.195
              c-7.917,0-14.309-6.425-14.309-14.34c0-7.885,6.393-14.31,14.309-14.31c7.915,0,14.308,6.425,14.308,14.31
              C410.499,209.771,404.107,216.195,396.191,216.195z"
							/>
						</svg>
						<span class="text-main-100">contents</span>
					</a>
				</div>
			</transition>
			<!-- video customizing button -->
			<transition name="slide-fade">
				<div
					:class="[
						state.isAnyModalOpen
							? ''
							: 'hover:bg-sub-300 w-full cursor-pointer',
					]"
					v-show="showVideoBtn"
					@click="clickVideoCustomizingBtn()"
				>
					<a class="h-24 px-6 flex flex-col justify-center items-center w-full">
						<svg
							class="sidebar-menu-icon h-8 w-8"
							viewBox="0 0 16 16"
							xmlns="http://www.w3.org/2000/svg"
						>
							<path
								d="M0 4s0-2 2-2h12s2 0 2 2v6s0 2-2 2h-4c0 .667.083 1.167.25 1.5H11a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1h.75c.167-.333.25-.833.25-1.5H2s-2 0-2-2V4zm1.398-.855a.758.758 0 0 0-.254.302A1.46 1.46 0 0 0 1 4.01V10c0 .325.078.502.145.602.07.105.17.188.302.254a1.464 1.464 0 0 0 .538.143L2.01 11H14c.325 0 .502-.078.602-.145a.758.758 0 0 0 .254-.302 1.464 1.464 0 0 0 .143-.538L15 9.99V4c0-.325-.078-.502-.145-.602a.757.757 0 0 0-.302-.254A1.46 1.46 0 0 0 13.99 3H2c-.325 0-.502.078-.602.145z"
							/>
						</svg>
						<span class="text-main-100">video customizing</span>
					</a>
				</div>
			</transition>
			<!-- audio customizing button -->
			<transition name="slide-fade">
				<div
					:class="[
						state.isAnyModalOpen
							? ''
							: 'hover:bg-sub-300 w-full cursor-pointer',
					]"
					v-show="showAudioBtn"
					@click="clickAudioCustomizingBtn()"
				>
					<a class="h-24 px-6 flex flex-col justify-center items-center w-full">
						<svg
							class="sidebar-menu-icon h-8 w-8"
							viewBox="0 0 1024 1024"
							xmlns="http://www.w3.org/2000/svg"
						>
							<path
								d="M842 454c0-4.4-3.6-8-8-8h-60c-4.4 0-8 3.6-8 8 0 140.3-113.7 254-254 254S258 594.3 258 454c0-4.4-3.6-8-8-8h-60c-4.4 0-8 3.6-8 8 0 168.7 126.6 307.9 290 327.6V884H326.7c-13.7 0-24.7 14.3-24.7 32v36c0 4.4 2.8 8 6.2 8h407.6c3.4 0 6.2-3.6 6.2-8v-36c0-17.7-11-32-24.7-32H548V782.1c165.3-18 294-158 294-328.1zM512 624c93.9 0 170-75.2 170-168V232c0-92.8-76.1-168-170-168s-170 75.2-170 168v224c0 92.8 76.1 168 170 168zm-94-392c0-50.6 41.9-92 94-92s94 41.4 94 92v224c0 50.6-41.9 92-94 92s-94-41.4-94-92V232z"
							/>
						</svg>
						<span class="text-main-100">audio customizing</span>
					</a>
				</div>
			</transition>
			<!-- timer button -->
			<transition name="slide-fade">
				<div
					:class="[
						state.isAnyModalOpen
							? ''
							: 'hover:bg-sub-300 w-full cursor-pointer',
					]"
					v-show="showTimerBtn"
					@click="clickTimer()"
				>
					<a class="h-24 px-6 flex flex-col justify-center items-center w-full">
						<svg
							class="sidebar-menu-icon h-7 w-7"
							version="1.1"
							xmlns="http://www.w3.org/2000/svg"
							viewBox="0 0 296.228 296.228"
							xmlns:xlink="http://www.w3.org/1999/xlink"
							enable-background="new 0 0 296.228 296.228"
						>
							<g>
								<path
									d="m167.364,48.003v-23.003h10.5c6.903,0 12.5-5.597 12.5-12.5s-5.596-12.5-12.5-12.5h-59.5c-6.903,0-12.5,5.597-12.5,12.5s5.597,12.5 12.5,12.5h10.5v23.003c-59.738,9.285-105.604,61.071-105.604,123.37-3.55271e-15,68.845 56.01,124.854 124.854,124.854s124.854-56.01 124.854-124.854c0-62.299-45.866-114.085-105.604-123.37zm-19.25,223.225c-55.06,0-99.854-44.795-99.854-99.854s44.795-99.854 99.854-99.854 99.854,44.795 99.854,99.854-44.794,99.854-99.854,99.854z"
								/>
								<path
									d="m160.614,166.18v-58.889c0-6.903-5.597-12.5-12.5-12.5s-12.5,5.597-12.5,12.5v66.1c0,2.033 0.81,3.982 2.25,5.416l34.969,34.822c4.893,4.872 12.806,4.854 17.678-0.037 4.871-4.892 4.854-12.807-0.037-17.678l-29.86-29.734z"
								/>
							</g>
						</svg>
						<span class="text-main-100">timer</span>
					</a>
				</div>
			</transition>
			<!-- roulette button -->
			<transition name="slide-fade">
				<div
					:class="[
						state.isAnyModalOpen
							? ''
							: 'hover:bg-sub-300 w-full cursor-pointer',
					]"
					v-show="showRouletteBtn"
					@click="clickRoulette()"
				>
					<a class="h-24 px-6 flex flex-col justify-center items-center w-full">
						<svg
							class="sidebar-menu-icon h-8 w-8"
							version="1.1"
							xmlns="http://www.w3.org/2000/svg"
							xmlns:xlink="http://www.w3.org/1999/xlink"
							x="0px"
							y="0px"
							viewBox="0 0 512 512"
							style="enable-background: new 0 0 512 512"
							xml:space="preserve"
						>
							<g>
								<path
									d="M456,232c0-47.576-16.991-93.676-47.844-129.807c-27.703-32.444-65.031-55.167-106.165-64.871l23.665-23.666
                c2.288-2.288,2.973-5.729,1.734-8.718S323.236,0,320,0H192c-3.236,0-6.153,1.949-7.391,4.938s-0.554,6.43,1.734,8.718
                l23.665,23.665c-41.122,9.702-78.441,32.415-106.143,64.847C72.999,138.303,56,184.412,56,232c0,87.908,57.01,162.751,136,189.499
                V456h-53c-6.065,0-11,4.935-11,11v37c0,4.418,3.582,8,8,8h240c4.418,0,8-3.582,8-8v-37c0-6.065-4.935-11-11-11h-53v-34.501
                C398.99,394.751,456,319.908,456,232z M300.687,16L256,60.687L211.313,16H300.687z M321.678,118.242
                c1.26,0.728,2.635,1.073,3.993,1.073c2.765,0,5.454-1.435,6.936-4.001l6.19-10.722c17.79,11.601,33.011,26.821,44.611,44.611
                l-81.552,47.084c-2.932,1.693-4.379,4.961-3.906,8.117c-0.423,1.797-0.235,3.747,0.682,5.514c3.562,6.86,5.367,14.29,5.367,22.082
                c0,26.467-21.533,48-48,48s-48-21.533-48-48s21.533-48,48-48c7.636,0,14.932,1.737,21.685,5.164
                c1.634,0.83,3.416,1.036,5.088,0.723c0.662,0.172,1.336,0.258,2.005,0.258c2.765,0,5.454-1.435,6.936-4.001l25.608-44.354
                c2.209-3.826,0.898-8.719-2.928-10.928c-3.826-2.21-8.719-0.899-10.928,2.928l-22.643,39.22c-5.396-2.28-11.019-3.791-16.821-4.508
                V80.21c21.848,1.138,42.489,6.912,60.957,16.353l-6.207,10.751C316.541,111.14,317.852,116.033,321.678,118.242z M360,240h47.79
                c-1.139,21.848-6.912,42.489-16.353,60.957l-76.448-44.137c2.231-5.283,3.755-10.931,4.493-16.82H328c4.418,0,8-3.582,8-8
                s-3.582-8-8-8h-8.508c-0.73-5.788-2.246-11.463-4.497-16.823l76.442-44.134c9.441,18.468,15.214,39.109,16.353,60.957H360
                c-4.418,0-8,3.582-8,8S355.582,240,360,240z M338.797,359.408l-44.137-76.447c4.635-3.525,8.776-7.666,12.301-12.301l76.447,44.137
                C371.807,332.587,356.587,347.807,338.797,359.408z M187.043,367.437l44.137-76.448c5.283,2.231,10.931,3.755,16.82,4.493v88.308
                C226.152,382.651,205.511,376.878,187.043,367.437z M128.592,314.797l76.447-44.137c3.525,4.635,7.666,8.776,12.301,12.301
                l-44.137,76.447C155.413,347.807,140.193,332.587,128.592,314.797z M173.203,104.592l44.137,76.447
                c-4.635,3.525-8.776,7.666-12.301,12.301l-76.447-44.137C140.193,131.413,155.413,116.193,173.203,104.592z M248,168.518
                c-5.889,0.738-11.537,2.261-16.82,4.493l-44.137-76.448C205.511,87.122,226.152,81.349,248,80.21V168.518z M120.563,163.043
                l76.448,44.137c-2.231,5.283-3.755,10.931-4.493,16.82H104.21C105.349,202.152,111.122,181.511,120.563,163.043z M192.518,240
                c0.738,5.889,2.262,11.537,4.493,16.82l-76.448,44.137c-9.441-18.468-15.214-39.109-16.353-60.957H192.518z M264,295.482
                c5.889-0.738,11.537-2.262,16.82-4.493l44.137,76.448c-18.468,9.441-39.109,15.214-60.957,16.353V295.482z M72,232
                c0-43.781,15.637-86.2,44.031-119.44c27.692-32.419,65.818-54.274,107.518-61.697l14.14,14.14
                c-23.465,2.555-45.496,9.954-65.062,21.186c-0.211,0.098-0.421,0.202-0.626,0.32c-0.251,0.145-0.49,0.303-0.72,0.47
                c-24.89,14.596-45.706,35.412-60.302,60.302c-0.167,0.229-0.325,0.469-0.47,0.72c-0.119,0.207-0.224,0.418-0.323,0.631
                C96.075,173.213,88,201.681,88,232s8.075,58.787,22.185,83.369c0.099,0.213,0.203,0.424,0.323,0.631
                c0.114,0.197,0.247,0.375,0.375,0.558c14.601,24.962,35.454,45.836,60.398,60.464c0.229,0.167,0.468,0.325,0.72,0.47
                c0.146,0.084,0.298,0.146,0.446,0.219C197.07,391.886,225.605,400,256,400c30.395,0,58.931-8.114,83.555-22.289
                c0.148-0.074,0.3-0.135,0.445-0.219c0.251-0.145,0.49-0.303,0.72-0.47c24.944-14.628,45.797-35.502,60.398-60.464
                c0.127-0.184,0.261-0.361,0.375-0.558c0.119-0.207,0.224-0.418,0.323-0.631C415.925,290.787,424,262.319,424,232
                s-8.075-58.787-22.185-83.369c-0.099-0.213-0.203-0.424-0.323-0.631c-0.145-0.251-0.303-0.49-0.47-0.719
                c-14.596-24.89-35.412-45.706-60.302-60.302c-0.229-0.167-0.468-0.325-0.72-0.47c-0.208-0.12-0.42-0.225-0.634-0.325
                c-19.565-11.229-41.593-18.626-65.055-21.181l14.14-14.14c41.71,7.425,79.845,29.29,107.538,61.72
                C424.37,145.82,440,188.229,440,232c0,101.458-82.542,184-184,184S72,333.458,72,232z M368,496H144v-24h224V496z M304,456h-96
                v-29.837c15.384,3.804,31.458,5.837,48,5.837s32.616-2.033,48-5.837V456z"
								/>
								<path
									d="M283.512,216.908l-13.158-3.879l-7.755-11.315c-1.492-2.177-3.96-3.478-6.599-3.478s-5.107,1.301-6.599,3.478
                l-7.755,11.315l-13.158,3.879c-2.531,0.746-4.531,2.692-5.346,5.201s-0.341,5.259,1.268,7.351l8.365,10.872l-0.377,13.712
                c-0.072,2.638,1.16,5.142,3.295,6.692s4.896,1.949,7.382,1.065L256,257.206l12.925,4.596c0.872,0.31,1.778,0.462,2.68,0.462
                c1.667,0,3.317-0.521,4.703-1.528c2.135-1.551,3.367-4.055,3.295-6.692l-0.377-13.712l8.365-10.872
                c1.609-2.091,2.083-4.841,1.268-7.351S286.043,217.654,283.512,216.908z M264.81,232.83c-1.122,1.458-1.707,3.259-1.657,5.099
                l0.135,4.887l-4.607-1.638c-0.867-0.308-1.773-0.462-2.68-0.462s-1.813,0.154-2.68,0.462l-4.607,1.638l0.135-4.887
                c0.05-1.839-0.535-3.64-1.657-5.099l-2.982-3.875l4.69-1.383c1.765-0.52,3.296-1.633,4.337-3.151l2.764-4.033l2.764,4.033
                c1.041,1.518,2.572,2.631,4.337,3.151l4.69,1.383L264.81,232.83z"
								/>
							</g>
						</svg>
						<span class="text-main-100">roulette</span>
					</a>
				</div>
			</transition>
			<!-- whiteboard button -->
			<transition name="slide-fade">
				<div
					:class="[
						state.isAnyModalOpen
							? ''
							: 'hover:bg-sub-300 w-full cursor-pointer',
					]"
					v-show="showWhiteboardBtn"
					@click="clickWhiteboardBtn()"
				>
					<a class="h-24 px-6 flex flex-col justify-center items-center w-full">
						<svg
							class="sidebar-menu-icon h-8 w-8"
							width="24px"
							height="24px"
							viewBox="0 0 24 24"
							version="1.1"
							xmlns="http://www.w3.org/2000/svg"
							xmlns:xlink="http://www.w3.org/1999/xlink"
						>
							<g id="🔍-Product-Icons" stroke="none" stroke-width="1">
								<g id="ic_fluent_whiteboard_24_regular">
									<path
										d="M15.989,4 L14.486,5.5 L5.25,5.5 L5.10647279,5.5058012 C4.20711027,5.57880766 3.5,6.3318266 3.5,7.25 L3.5,9.49 L6.11032966,7.8500658 C7.11458474,7.21884459 8.42578385,7.4788108 9.11805049,8.41878234 L9.21263533,8.55766707 C9.60754263,9.18595342 9.66531956,9.96466959 9.37643726,10.6398047 L9.29698911,10.8062836 L8.08925622,13.0916763 C7.8957274,13.4579003 8.03572001,13.911669 8.4019376,14.1051994 C8.59519714,14.2073288 8.82158817,14.2192865 9.02227723,14.1418514 L9.12020755,14.0956881 L10.8791978,13.102709 C11.2392458,12.8979206 11.6971362,13.0237834 11.9019246,13.3838314 C12.0896473,13.7138755 11.9995312,14.1261343 11.7058795,14.3502913 L11.6208022,14.4065583 L9.85592161,15.4028701 C9.18921021,15.7781116 8.37750848,15.7888601 7.70109472,15.4314042 C6.6502057,14.8760541 6.22026466,13.6064091 6.69296688,12.535549 L6.76304763,12.3908405 L7.97078427,10.1054407 C8.09599053,9.86851294 8.08527176,9.58278305 7.94266635,9.35590187 C7.74227866,9.03709073 7.34143087,8.9208341 7.00672527,9.06794099 L6.90856446,9.12003478 L3.5,11.262 L3.5,16.754591 C3.5,17.7210893 4.28350169,18.504591 5.25,18.504591 L18.75,18.504591 C19.7164983,18.504591 20.5,17.7210893 20.5,16.754591 L20.5,9.443 L22,7.946 L22,16.754591 C22,18.5495164 20.5449254,20.004591 18.75,20.004591 L5.25,20.004591 C3.45507456,20.004591 2,18.5495164 2,16.754591 L2,7.25 C2,5.51696854 3.35645477,4.10075407 5.06557609,4.00514479 L5.25,4 L15.989,4 Z M21.175499,3.54486123 L21.3057053,3.66549405 L21.4262125,3.79573486 C22.1894032,4.68737541 22.1487912,6.0305334 21.3046801,6.87410524 L17.0238193,11.1477533 C16.760843,11.4102865 16.4369385,11.6036013 16.0810348,11.7104319 L13.7534665,12.409092 C13.224498,12.5678711 12.6669685,12.2677732 12.5081894,11.7388048 C12.4499495,11.54478 12.4520104,11.3376424 12.5140993,11.1448148 L13.2530235,8.84996054 C13.3626608,8.50946335 13.5519023,8.20001385 13.805051,7.94727999 L18.095697,3.66366511 C18.9405104,2.82023607 20.2840592,2.78081537 21.175499,3.54486123 Z M19.155487,4.72519469 L14.864841,9.00880957 C14.7804581,9.09305419 14.7173776,9.19620402 14.6808318,9.30970309 L14.2335301,10.6988744 L15.6497924,10.2737587 C15.768427,10.2381485 15.8763951,10.1737102 15.9640539,10.0861992 L20.244074,5.81339079 L20.3188279,5.72689957 C20.5422483,5.42598751 20.517571,4.99885441 20.2447063,4.72581529 C19.9440539,4.42497074 19.456482,4.42469294 19.155487,4.72519469 Z"
										id="🎨-Color"
									></path>
								</g>
							</g>
						</svg>
						<span class="text-main-100">whiteboard</span>
					</a>
				</div>
			</transition>
			<!-- vote button -->
			<transition name="slide-fade">
				<div
					:class="[
						state.isAnyModalOpen
							? ''
							: 'hover:bg-sub-300 w-full cursor-pointer',
					]"
					v-show="showVoteBtn"
					@click="clickVote()"
					@startVote="sendVote"
				>
					<a class="h-24 px-6 flex flex-col justify-center items-center w-full">
						<svg
							class="sidebar-menu-icon h-8 w-8"
							viewBox="0 0 24 24"
							xmlns="http://www.w3.org/2000/svg"
						>
							<path
								d="M20.498 15.5H3.5V20.5H20.498V15.5ZM21.9445 14.4719L21.9661 14.5336L21.9892 14.6345L21.9981 14.7331V21.25C21.9981 21.6297 21.7159 21.9435 21.3499 21.9932L21.2481 22H2.75C2.3703 22 2.05651 21.7178 2.00685 21.3518L2 21.25V14.7506L2.00184 14.6977L2.01271 14.6122C2.02285 14.5584 2.03841 14.5072 2.05894 14.4587L4.81824 8.44003C4.92517 8.2068 5.14245 8.04682 5.39153 8.01047L5.5 8.0026L8.03982 8.00183L7.25089 9.37206L7.18282 9.50183L5.981 9.502L3.918 13.9998H20.07L18.0428 9.65383L18.9052 8.15653C18.9718 8.20739 19.0301 8.26957 19.0771 8.3411L19.1297 8.43553L21.9445 14.4719ZM13.3652 2.05565L13.4566 2.10062L18.6447 5.10375C18.9729 5.29371 19.1033 5.69521 18.9636 6.03728L18.9187 6.1289L16.112 11.001L17.25 11.0016C17.6642 11.0016 18 11.3374 18 11.7516C18 12.1313 17.7178 12.4451 17.3518 12.4948L17.25 12.5016L15.248 12.501L15.2471 12.504H11.1691L11.166 12.501L6.75 12.5016C6.33579 12.5016 6 12.1658 6 11.7516C6 11.3719 6.28215 11.0581 6.64823 11.0085L6.75 11.0016L8.573 11.001L8.39145 10.8963C8.06327 10.7063 7.93285 10.3048 8.0726 9.96272L8.11747 9.8711L12.4341 2.37536C12.6235 2.04633 13.024 1.91557 13.3652 2.05565ZM13.3559 3.77529L9.78781 9.97119L11.566 11.001H14.383L17.248 6.02818L13.3559 3.77529Z"
							/>
						</svg>
						<span class="text-main-100">vote</span>
					</a>
				</div>
			</transition>
			<!-- back button -->
			<transition name="slide-fade">
				<div
					class="hover:bg-sub-300 w-full cursor-pointer"
					v-show="showBackBtn"
					@click="clickBackBtn"
				>
					<a class="h-24 px-6 flex flex-col justify-center items-center w-full">
						<svg
							class="sidebar-menu-icon h-8 w-8"
							version="1.1"
							xmlns="http://www.w3.org/2000/svg"
							xmlns:xlink="http://www.w3.org/1999/xlink"
							x="0px"
							y="0px"
							viewBox="0 0 52.502 52.502"
							style="enable-background: new 0 0 52.502 52.502"
							xml:space="preserve"
						>
							<path
								d="M51.718,50.857l-1.341-2.252C40.075,31.295,25.975,32.357,22.524,32.917v13.642L0,23.995L22.524,1.644v13.43
              c0.115,0,0.229-0.001,0.344-0.001c12.517,0,18.294,5.264,18.542,5.496c13.781,11.465,10.839,27.554,10.808,27.715L51.718,50.857z
              M25.505,30.735c5.799,0,16.479,1.923,24.993,14.345c0.128-4.872-0.896-15.095-10.41-23.012c-0.099-0.088-5.935-5.364-18.533-4.975
              l-1.03,0.03V6.447L2.832,24.001l17.692,17.724V31.311l0.76-0.188C21.338,31.109,22.947,30.735,25.505,30.735z"
							/>
						</svg>
						<span class="text-main-100">back</span>
					</a>
				</div>
			</transition>
		</div>
	</div>
	<vote-create-modal
		ref="voteCreateModal"
		@startVote="sendVote"
		@sendVoteResult="sendVoteResult"
		@closeModal="closeModal"
	/>
	<roulette-create-modal
		ref="rouletteCreateModal"
		@sendRoulletteMessage="sendRoulletteMessage"
		@closeModal="closeModal"
	/>
	<video-customize-modal
		ref="videoCustomizeModal"
		@stickerOverlay="stickerOverlay"
		@visualFilter="visualFilter"
		@textOverlay="textOverlay"
		@filterOff="filterOff"
		@bottombarFilterBtn="bottombarFilterBtn"
		@closeModal="closeModal"
	/>
	<audio-customize-modal
		ref="audioCustomizeModal"
		@voiceFilter="voiceFilter"
		@filterOff="filterOff"
		@bottombarFilterBtn="bottombarFilterBtn"
		@closeModal="closeModal"
	/>
	<timer-create-modal ref="timerCreateModal" @closeModal="closeModal" />
</template>

<style>
.sidebar-menu-icon {
	fill: #6667ab;
}
.slide-fade-enter-active {
	transition: all 0.3s ease-out;
}
.slide-fade-leave-active {
	transition: none;
}
.slide-fade-leave-to {
	transform: none;
	opacity: 0;
}
.slide-fade-leave-from {
	transform: none;
	opacity: 0;
}
.slide-fade-enter-from {
	transform: translateX(-20px);
	opacity: 0;
}
</style>

<script>
import { ref, reactive } from 'vue';
import VoteCreateModal from '@/teleport/vote-create-modal.vue';
import videoCustomizeModal from '@/teleport/video-customize-modal.vue';
import audioCustomizeModal from '@/teleport/audio-customize-modal.vue';
import timerCreateModal from '@/teleport/timer-create-modal.vue';
import rouletteCreateModal from '@/teleport/roulette-create-modal';

export default {
	name: 'room-sidebar',

	components: {
		VoteCreateModal,
		rouletteCreateModal,
		videoCustomizeModal,
		audioCustomizeModal,
		timerCreateModal,
	},
	setup(props, { emit }) {
		const voteCreateModal = ref(null);
		const rouletteCreateModal = ref(null);
		const videoCustomizeModal = ref(null);
		const audioCustomizeModal = ref(null);
		const timerCreateModal = ref(null);

		const state = reactive({
			isAnyModalOpen: false,
		});

		const clickVote = () => {
			console.log('clickvote');
			// 다른 모달 오픈 상태에서는 실행 금지
			if (state.isAnyModalOpen) return;
			voteCreateModal.value.open();
			state.isAnyModalOpen = true;
		};

		const clickTimer = () => {
			console.log('clickTimer');
			// 다른 모달 오픈 상태에서는 실행 금지
			if (state.isAnyModalOpen) return;
			timerCreateModal.value.open();
			state.isAnyModalOpen = true;
		};

		const clickVideoCustomizingBtn = () => {
			// 다른 모달 오픈 상태에서는 실행 금지
			if (state.isAnyModalOpen) return;
			videoCustomizeModal.value.open();
			state.isAnyModalOpen = true;
		};

		const clickAudioCustomizingBtn = () => {
			// 다른 모달 오픈 상태에서는 실행 금지
			if (state.isAnyModalOpen) return;
			audioCustomizeModal.value.open();
			state.isAnyModalOpen = true;
		};

		const clickRoulette = () => {
			console.log('click roulette');
			// 다른 모달 오픈 상태에서는 실행 금지
			if (state.isAnyModalOpen) return;
			rouletteCreateModal.value.open();
			state.isAnyModalOpen = true;
		};

		const clickWhiteboardBtn = () => {
			console.log('click whiteboard');
			// 다른 모달 오픈 상태에서는 실행 금지
			if (state.isAnyModalOpen) return;
			emit('toggle-whiteboard');
		};

		const sendVote = voteInfo => {
			emit('startVote', { voteInfo });
		};

		const sendVoteResult = () => {
			emit('sendVoteResult');
		};

		const stickerOverlay = filterInfo => {
			console.log('스티커오버레이 사이드바 emit : ' + filterInfo);
			emit('stickerOverlay', filterInfo);
		};

		const visualFilter = filterInfo => {
			emit('visualFilter', filterInfo);
		};

		const textOverlay = filterInfo => {
			emit('textOverlay', filterInfo);
		};

		const voiceFilter = filterInfo => {
			emit('voiceFilter', filterInfo);
		};

		const filterOff = () => {
			emit('filterOff');
		};

		const bottombarFilterBtn = btnState => {
			emit('bottombarFilterBtn', btnState);
		};

		const closeModal = () => {
			state.isAnyModalOpen = false;
		};

		return {
			state,
			clickTimer,
			voteCreateModal,
			rouletteCreateModal,
			videoCustomizeModal,
			audioCustomizeModal,
			clickVote,
			clickVideoCustomizingBtn,
			clickAudioCustomizingBtn,
			clickRoulette,
			clickWhiteboardBtn,
			timerCreateModal,
			sendVote,
			sendVoteResult,
			stickerOverlay,
			visualFilter,
			textOverlay,
			voiceFilter,
			filterOff,
			bottombarFilterBtn,
			closeModal,
		};
	},

	data() {
		return {
			isSidebarOpen: true,
			// right: false,

			showContentBtn: true,
			showVideoBtn: true,
			showAudioBtn: true,

			showTimerBtn: false,
			showRouletteBtn: false,
			showWhiteboardBtn: false,
			showVoteBtn: false,

			showBackBtn: false,
		};
	},

	methods: {
		toggle() {
			console.log(this.isSidebarOpen);
			this.isSidebarOpen = !this.isSidebarOpen;
			console.log(this.isSidebarOpen);
		},

		resetBtns() {
			this.showContentBtn = false;
			this.showVideoBtn = false;
			this.showAudioBtn = false;

			this.showTimerBtn = false;
			this.showRouletteBtn = false;
			this.showWhiteboardBtn = false;
			this.showVoteBtn = false;
			this.showBackBtn = false;
		},

		clickContentBtn() {
			this.resetBtns();

			this.showTimerBtn = true;
			this.showRouletteBtn = true;
			this.showWhiteboardBtn = true;
			this.showVoteBtn = true;
			this.showBackBtn = true;
		},

		clickBackBtn() {
			this.resetBtns();

			this.showContentBtn = true;
			this.showVideoBtn = true;
			this.showAudioBtn = true;
		},

		startVote(voteInfo) {
			this.state.isAnyModalOpen = true;
			this.$refs.voteCreateModal.startVote(voteInfo);
		},
		// 룰렛 생성 모달(하위)에서 받은 data를 파티룸 내부 컴포넌트(상위)로 전달(emit)
		sendRoulletteMessage(rouletteTopic) {
			this.$emit('sendRoulletteMessage', rouletteTopic);
		},
	},
};
</script>
